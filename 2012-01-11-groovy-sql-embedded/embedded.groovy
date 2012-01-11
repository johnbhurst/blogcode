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

db.shutdown()

