package org.example.mycartcalculator.util

import androidx.compose.ui.graphics.Color
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime

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

@OptIn(ExperimentalTime::class)
fun Long.formatDate(): String {
    val dateTime = Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth.toString().padStart(2, '0')
    val month = dateTime.monthNumber.toString().padStart(2, '0')
    val year = (dateTime.year % 100).toString().padStart(2, '0')

    return "$day/$month/$year"
}

fun String.avatarColor(): Color {
    val colors = listOf(
        Color(0xFF4CAF50),
        Color(0xFF2196F3),
        Color(0xFFFF9800),
        Color(0xFF9C27B0),
        Color(0xFFF44336),
        Color(0xFF009688)
    )
    return colors[this.hashCode().absoluteValue % colors.size]
}
