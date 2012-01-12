@Grab("org.springframework:spring-jdbc:3.1.0.RELEASE")
@Grab("com.h2database:h2:1.3.163")
@Grab("com.googlecode.usc:jdbcdslog:1.0.5")
@Grapes([
  @Grab("ch.qos.logback:logback-classic:0.9.30"),
  @Grab("org.slf4j:jcl-over-slf4j:1.6.4"),
  @Grab("org.slf4j:jul-to-slf4j:1.6.4"),
  @Grab("org.slf4j:slf4j-api:1.6.4"),
  @GrabExclude("slf4j:slf4j-api"),
  @GrabConfig(systemClassLoader = true)
])
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.jdbcdslog.ConnectionPoolDataSourceProxy
import groovy.sql.Sql
import org.slf4j.bridge.SLF4JBridgeHandler
//import java.util.logging.Logger
//import java.util.logging.LogManager

//Logger log = LogManager.logManager.getLogger('')
//while (log.parent) {
//  log = log.parent
//}
//log.with {
//  handlers.each { removeHandler(it) }
//  addHandler(new SLF4JBridgeHandler())
//}

SLF4JBridgeHandler.install()
//Logger log = LogManager.logManager.getLogger('')
//while (log.parent) {
//  log = log.parent
//}
//log.with {
//  handlers.each { removeHandler(it) }
//  //addHandler(new SLF4JBridgeHandler())
//}


//java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("org.wombat")
//println julLogger
//julLogger.handlers.each {println it.level}
//julLogger.addHandler(new SLF4JBridgeHandler(level: java.util.logging.Level.FINEST))
//julLogger.fine("hello world")

def builder = new EmbeddedDatabaseBuilder()
def db = builder.setType(EmbeddedDatabaseType.H2).addScript("birt-h2.sql").build()
def sql = new Sql(new ConnectionPoolDataSourceProxy(targetDSDirect: db))
//def sql = new Sql(db)

def c = sql.firstRow("SELECT COUNT(*) c FROM offices").c
assert c == 7

def state = "CA"
def country = "USA"
def office = sql.firstRow("SELECT * FROM offices WHERE state = ? AND country = ?", state, country)
////def office = sql.firstRow("SELECT * FROM offices WHERE state = ${-> state} AND country = ${-> country}")
assert office.city == "San Francisco"

//sql.withTransaction {conn ->
//  def stmt = conn.prepareStatement("SELECT * FROM offices WHERE state = ? AND country = ?")
//  stmt.setString(1, state)
//  stmt.setString(2, country)
//  def rs = stmt.executeQuery()
//  while (rs.next()) {
//    assert rs.getString("CITY") == "San Francisco"
//  }
//
//  def stmt2 = conn.prepareStatement("SELECT * FROM offices WHERE state = 'CA' AND country = 'USA'")
//  //stmt.setString(1, state)
//  //stmt.setString(2, country)
//  def rs2 = stmt2.executeQuery()
//  while (rs2.next()) {
//    assert rs2.getString("CITY") == "San Francisco"
//  }
//
////  def stmt2 = conn.createStatement()
////  def rs2 = stmt2.executeQuery("SELECT * FROM offices WHERE state = 'CA' AND country = 'USA'")
////  while (rs2.next()) {
////    assert rs2.getString("CITY") == "San Francisco"
////  }
//}


db.shutdown()

