package com.mackhartley.temptracker.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

val formatDateShort: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
val formatDateMedium: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
val formatTime: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")
val isoFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME // Used for DB

fun OffsetDateTime.toDBFormattedString() : String {
    return this.format(isoFormatter)
}

fun String.toOffsetDateTimeFromDBString() : OffsetDateTime {
    return isoFormatter.parse(this, OffsetDateTime::from)
}

fun LocalDate.toStandardFormat(short: Boolean = false): String {
    return if (short) {
        this.format(formatDateShort)
    } else {
        return this.format(formatDateMedium)
    }
}

fun OffsetDateTime.toStandardFormat(short: Boolean = false): String {
    return if (short) {
        this.format(formatDateShort)
    } else {
        return this.format(formatDateMedium)
    }
}