package otus.otuskotlin.hub.logging.logback

import ch.qos.logback.classic.Logger
import com.otus.otuskotlin.hub.logging.common.IHubLogWrapper
import org.example.com.otus.otuskotlin.hub.logging.logback.HubLogWrapperLogback
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun hubLoggerLogback(logger: Logger): IHubLogWrapper = HubLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun hubLoggerLogback(clazz: KClass<*>): IHubLogWrapper = hubLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun hubLoggerLogback(loggerId: String): IHubLogWrapper = hubLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)