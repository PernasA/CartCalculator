package org.example.mycartcalculator.util

import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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