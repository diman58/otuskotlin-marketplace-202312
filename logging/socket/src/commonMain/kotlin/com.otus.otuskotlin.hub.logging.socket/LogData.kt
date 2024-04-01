package com.otus.otuskotlin.hub.logging.socket

import LogLevel
import kotlinx.serialization.Serializable

@Serializable
data class LogData(
    val level: LogLevel,
    val message: String,
//    val data: T
)
