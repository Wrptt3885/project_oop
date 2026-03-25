package com.nexfin.frontend.utils

object AppLogger {
    fun info(tag: String, message: String) {
        if (Constants.DebugLogging) {
            println("[NexFin][$tag] $message")
        }
    }

    fun error(tag: String, message: String, throwable: Throwable? = null) {
        if (Constants.DebugLogging) {
            println("[NexFin][$tag][ERROR] $message")
            throwable?.printStackTrace()
        }
    }
}
