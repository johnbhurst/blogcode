// $Id$
// Copyright 2010 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2010-12-29

package nz.co.skepticalhumorist.poi

import org.junit.Test
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

class HSSFWorkbookBuilderTest extends AbstractWorkbookTest {

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
    assertWorkbook(workbook)
  }

}

