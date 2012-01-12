@Grab("org.springframework:spring-jdbc:3.1.0.RELEASE")
@Grab("com.h2database:h2:1.3.163")
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import groovy.sql.Sql

def builder = new EmbeddedDatabaseBuilder()
def db = builder.setType(EmbeddedDatabaseType.H2).addScript("birt-h2.sql").build()
def sql = new Sql(db)

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

