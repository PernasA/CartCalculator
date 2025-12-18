package org.example.mycartcalculator.util

import kotlin.math.roundToInt

fun Double.formatPrice(): String {
    val value = (this * 100).roundToInt()
    val integerPart = value / 100
    val decimalPart = value % 100
    return "$ $integerPart.${decimalPart.toString().padStart(2, '0')}"
}