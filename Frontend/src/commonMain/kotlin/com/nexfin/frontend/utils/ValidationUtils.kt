package com.nexfin.frontend.utils

object ValidationUtils {
    fun isValidEmail(value: String): Boolean {
        return value.contains("@") && value.contains(".") && value.length >= 5
    }

    fun isValidPassword(value: String): Boolean = value.length >= 8

    fun isPositiveAmount(value: String): Boolean = value.toDoubleOrNull()?.let { it > 0.0 } == true
}
