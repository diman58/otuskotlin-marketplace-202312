package com.otus.otuskotlin.hub.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class HubLoggerProvider(
    private val provider: (String) -> IHubLogWrapper = { IHubLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>) = provider(function.name)
}