// $Id$
// Copyright 2010 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2010-12-29

package nz.co.skepticalhumorist.poi

import org.junit.Test
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFRichTextString

class GroovyPOITest extends AbstractWorkbookTest {

  @Test
  void testOk() {
    def workbook = new HSSFWorkbook()
    def sheet1 = workbook.createSheet("Data")
    def row10 = sheet1.createRow(0)
    row10.createCell(0).setCellValue(new HSSFRichTextString("Invoice Number"))
    row10.createCell(1).setCellValue(new HSSFRichTextString("Invoice Date"))
    row10.createCell(2).setCellValue(new HSSFRichTextString("Amount"))
    def row11 = sheet1.createRow(1)
    row11.createCell(0).setCellValue(new HSSFRichTextString("100"))
    row11.createCell(1).setCellValue(Date.parse("yyyy-MM-dd", "2010-10-18"))
    row11.createCell(2).setCellValue(123.45)
    def row12 = sheet1.createRow(2)
    row12.createCell(0).setCellValue(new HSSFRichTextString("600"))
    row12.createCell(1).setCellValue(Date.parse("yyyy-MM-dd", "2010-11-17"))
    row12.createCell(2).setCellValue(132.54)
    def sheet2 = workbook.createSheet("Summary")
    def row20 = sheet2.createRow(0)
    row20.createCell(0).setCellValue(new HSSFRichTextString("Sheet: Summary"))
    def row21 = sheet2.createRow(1)
    row21.createCell(0).setCellValue(new HSSFRichTextString("Total"))
    row21.createCell(1).setCellValue(123.45 + 132.54)

    assertWorkbook(workbook)
  }


}
