// $Id$
// Copyright 2010 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2010-12-29

package nz.co.skepticalhumorist.poi

import org.junit.Test
import org.apache.poi.ss.usermodel.Sheet

class HSSFWorkbookBuilderTest {

  @Test
  void testOk() {
    def workbook = new HSSFWorkbookBuilder().workbook {
      sheet("Data") { // sheet1
        row(["Invoice Number", "Invoice Date", "Amount"])
        row(["100", Date.parse("yyyy-MM-dd", "2010-10-18"), 123.45])
        row(["600", Date.parse("yyyy-MM-dd", "2010-11-17"), 132.54])
      }
      sheet("Summary") { // sheet2
        row(["Sheet: Summary"])
        row(["Total", 123.45 + 132.54])
      }
    }

    assert workbook.numberOfSheets == 2
    Sheet sheet1 = workbook.getSheet("Data")
    assert sheet1.getRow(0).getCell(0).stringCellValue == "Invoice Number"
    assert sheet1.getRow(0).getCell(1).stringCellValue == "Invoice Date"
    assert sheet1.getRow(0).getCell(2).stringCellValue == "Amount"
    assert sheet1.getRow(1).getCell(0).stringCellValue == "100"
    assert sheet1.getRow(1).getCell(1).dateCellValue == Date.parse("yyyy-MM-dd", "2010-10-18")
    assert sheet1.getRow(1).getCell(2).numericCellValue == 123.45
    assert sheet1.getRow(2).getCell(0).stringCellValue == "600"
    assert sheet1.getRow(2).getCell(1).dateCellValue == Date.parse("yyyy-MM-dd", "2010-11-17")
    assert sheet1.getRow(2).getCell(2).numericCellValue == 132.54
    assert sheet1.getPhysicalNumberOfRows() == 3
    assert sheet1.lastRowNum == 2
    Sheet sheet2 = workbook.getSheet("Summary")
    assert sheet2.getRow(0).getCell(0).stringCellValue == "Sheet: Summary"
    assert sheet2.getRow(1).getCell(0).stringCellValue == "Total"
    assert sheet2.getRow(1).getCell(1).numericCellValue == 123.45 + 132.54
    assert sheet2.getPhysicalNumberOfRows() == 2
    assert sheet2.lastRowNum == 1
  }
}

