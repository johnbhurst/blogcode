// $Id$
// Copyright 2011 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-08

package nz.co.skepticalhumorist.dbunit

import org.junit.Test
import org.dbunit.dataset.IDataSet

class DataSetBuilderTest {

  @Test
  void testOk() {
    IDataSet dataSet = new DataSetBuilder().dataSet {
      table("employee") {
        columns("id", "name", "department", "active_yn")
        row("1", "Joe Smith", "1", "Y")
      }
      table("department") {
        columnsCsv("id,name,active_yn")
        rowCsv("1,HR,Y")
        rowCsv("2,IT,Y")
        rowCsv("3,Marketing,N")
      }
    }
  }

}
