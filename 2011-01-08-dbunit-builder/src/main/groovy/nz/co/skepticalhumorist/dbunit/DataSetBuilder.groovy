// $Id$
// Copyright 2011 John Hurst
// John Hurst (john.b.hurst@gmail.com)
// 2011-01-08

package nz.co.skepticalhumorist.dbunit

import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.DefaultDataSet
import org.dbunit.dataset.DefaultTable

class DataSetBuilder {

  DefaultDataSet result = new DefaultDataSet()
  DefaultTable table

  IDataSet dataSet(Closure closure) {
    closure.delegate = this
    closure.call()
    result
  }

  void table(Map props, String name, Closure closure) {
    table = new DefaultTable(name)
    closure.delegate = this
    closure.call()
    result.addTable(table)
  }

  void table(String name, Closure closure) {
    table([:], name, closure)
  }

  void columns(String... columns) {

  }

  void columnsCsv(String csvColumns) {

  }

  void row(String... data) {

  }

  void rowCsv(String csvData) {

  }

}
