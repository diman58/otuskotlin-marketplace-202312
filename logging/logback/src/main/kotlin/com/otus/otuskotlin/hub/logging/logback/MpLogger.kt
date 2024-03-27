package otus.otuskotlin.hub.logging.logback

import ch.qos.logback.classic.Logger
import com.otus.otuskotlin.hub.logging.common.IMpLogWrapper
import org.example.com.otus.otuskotlin.hub.logging.logback.MpLogWrapperLogback
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun mpLoggerLogback(logger: Logger): IMpLogWrapper = MpLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun mpLoggerLogback(clazz: KClass<*>): IMpLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun mpLoggerLogback(loggerId: String): IMpLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)