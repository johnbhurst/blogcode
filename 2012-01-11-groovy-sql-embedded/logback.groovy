// Copyright 2011 Red Energy
// John Hurst (john.b.hurst@gmail.com)
// 2011-11-23

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.OFF
import static ch.qos.logback.classic.Level.WARN

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

