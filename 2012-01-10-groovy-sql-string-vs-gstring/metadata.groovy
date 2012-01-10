
@Grab(group='com.oracle', module='ojdbc6', version='11.2.0.3.0')
import oracle.jdbc.pool.OracleDataSource
import groovy.sql.Sql

String connectionString = args[0]
Sql db = new Sql(new OracleDataSource(URL: "jdbc:oracle:thin:$connectionString"))

def tables = db.rows("SELECT table_name FROM user_tables ORDER BY 1")*.table_name
println tables.size()

tables.each {tableName ->
  //def rowCount = db.firstRow("SELECT COUNT(*) c FROM $tableName")*.c
  def rowCount = db.firstRow("SELECT COUNT(*) c FROM $tableName".toString()).c
  println "$tableName: $rowCount"
}

def printHeader = {metaData ->
  println metaData*.columnName.join(",")
}
db.eachRow("SELECT * FROM offices", printHeader) {row ->
  println row.toRowResult().values().join(",")
}



