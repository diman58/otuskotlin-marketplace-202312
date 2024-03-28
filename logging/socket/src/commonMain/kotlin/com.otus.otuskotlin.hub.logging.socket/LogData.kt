package com.otus.otuskotlin.hub.logging.socket

import com.otus.otuskotlin.hub.logging.common.LogLevel
import kotlinx.serialization.Serializable

@Serializable
data class LogData(
    val level: LogLevel,
    val message: String,
//    val data: T
)
