// Run with -cp . -Djava.util.logging.config.file=jul-to-slf4j.properties

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
import org.slf4j.bridge.SLF4JBridgeHandler
import groovy.sql.Sql

SLF4JBridgeHandler.install()

def builder = new EmbeddedDatabaseBuilder()
def db = builder.setType(EmbeddedDatabaseType.H2).addScript("birt-h2.sql").build()
def sql = new Sql(new ConnectionPoolDataSourceProxy(targetDSDirect: db))

def c = sql.firstRow("SELECT COUNT(*) c FROM offices").c
assert c == 7

def state = "CA"
def country = "USA"

def office0 = sql.firstRow("SELECT * FROM offices WHERE state = 'CA' AND country = 'USA'")
assert office0.city == "San Francisco"

def office1 = sql.firstRow("SELECT * FROM offices WHERE state = ? AND country = ?", state, country)
assert office1.city == "San Francisco"

def office2 = sql.firstRow("SELECT * FROM offices WHERE state = ? AND country = ?", [state, country])
assert office2.city == "San Francisco"

def office3 = sql.firstRow("SELECT * FROM offices WHERE state = $state AND country = $country")
assert office3.city == "San Francisco"

db.shutdown()

