package com.nexfin.frontend.utils

import kotlin.math.absoluteValue

object CurrencyFormatter {
    fun format(amount: Double, currency: String = "THB"): String {
        val safeAmount = if (amount.isFinite()) amount else 0.0
        val sign = if (safeAmount < 0) "-" else ""
        val absolute = safeAmount.absoluteValue
        return when (currency.uppercase()) {
            "THB" -> "$sign฿${"%,.2f".format(absolute)}"
            "USD" -> "$sign$${"%,.2f".format(absolute)}"
            else -> "$sign${"%,.2f".format(absolute)} $currency"
        }
    }
}
