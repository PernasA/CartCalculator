package org.example.mycartcalculator.util

import kotlin.math.roundToInt

fun Double.formatPrice(): String {
    val totalCents = (this * 100).roundToInt()

    val integerPart = totalCents / 100
    val decimalPart = totalCents % 100

    val integerStr = integerPart.toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()

    return "$$integerStr,${decimalPart.toString().padStart(2, '0')}"
}
