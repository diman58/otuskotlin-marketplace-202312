package otus.otuskotlin.hub.logging.logback

import ch.qos.logback.classic.Logger
import com.otus.otuskotlin.hub.logging.common.IHubLogWrapper
import org.example.com.otus.otuskotlin.hub.logging.logback.HubLogWrapperLogback
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun mpLoggerLogback(logger: Logger): IHubLogWrapper = HubLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun mpLoggerLogback(clazz: KClass<*>): IHubLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun mpLoggerLogback(loggerId: String): IHubLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)