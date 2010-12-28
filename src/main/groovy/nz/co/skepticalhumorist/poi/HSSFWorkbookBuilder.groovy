// $Id$
// Copyright 2010 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2010-12-29

package nz.co.skepticalhumorist.poi

import org.apache.poi.hssf.usermodel.HSSFRichTextString
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

class HSSFWorkbookBuilder {

  private Workbook workbook = new HSSFWorkbook()
  private Sheet sheet
  private int rows

  Workbook workbook(Closure closure) {
    closure.delegate = this
    closure.call()
    workbook
  }

  void sheet(String name, Closure closure) {
    sheet = workbook.createSheet(name)
    rows = 0
    closure.delegate = this
    closure.call()
  }

  void row(values) {
    Row row = sheet.createRow(rows++ as int)
    values.eachWithIndex {value, col ->
      Cell cell = row.createCell(col)
      switch (value) {
        case Date: cell.setCellValue((Date) value); break
        case Double: cell.setCellValue((Double) value); break
        case BigDecimal: cell.setCellValue(((BigDecimal) value).doubleValue()); break
        default: cell.setCellValue(new HSSFRichTextString("" + value)); break
      }
    }
  }

}
