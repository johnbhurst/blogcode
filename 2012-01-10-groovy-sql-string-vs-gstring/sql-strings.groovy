@Grab(group='com.oracle', module='ojdbc6', version='11.2.0.3.0')
import oracle.jdbc.pool.OracleDataSource
import groovy.sql.Sql

String connectionString = args[0]
Sql db = new Sql(new OracleDataSource(URL: "jdbc:oracle:thin:$connectionString"))

// Normal use of query parameters:

def office1 = db.firstRow("SELECT * FROM offices WHERE state = ? AND country = ?", ["CA", "USA"])
assert office1.city == "San Francisco"

def office2 = db.firstRow("SELECT * FROM offices WHERE state = ? AND country = ?", "CA", "USA")
assert office2.city == "San Francisco"

// Using GString:

def state = "CA"
def country = "USA"
def office3 = db.firstRow("SELECT * FROM offices WHERE state = $state AND country = $country")
assert office3.city == "San Francisco"

// Where variable is *not* a SQL placeholder:

def tables = db.rows("SELECT table_name FROM user_tables ORDER BY 1")*.table_name
println tables.size()

tables.each {tableName ->
  //def rowCount = db.firstRow("SELECT COUNT(*) c FROM $tableName")*.c
  def rowCount = db.firstRow("SELECT COUNT(*) c FROM $tableName".toString()).c
  println "$tableName: $rowCount"
}

