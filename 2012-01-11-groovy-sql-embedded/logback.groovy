import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.classic.Level.OFF

String PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} %level [%thread] %logger - %msg%n"

appender("CONSOLE", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = PATTERN
  }
}

root(INFO, ["CONSOLE"])

logger("org.jdbcdslog.ConnectionLogger", OFF)
logger("org.jdbcdslog.StatementLogger", INFO)
logger("org.jdbcdslog.SlowQueryLogger", INFO)
logger("org.jdbcdslog.ResultSetLogger", OFF)
logger("org.springframework", WARN)
logger("groovy.sql", DEBUG)
