package com.nexfin.frontend.utils

object DateFormatter {
    fun compact(isoDateTime: String): String {
        return isoDateTime
            .replace("T", "  ")
            .replace("Z", "")
            .take(16)
            .trim()
    }
}
