package com.mackhartley.temptracker.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import kotlin.math.roundToLong

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

fun createLocalDateFromDatePickerCallback(year: Int, month: Int, day: Int) : LocalDate {
    val realMonth = month + 1 //Idk why this is 0 indexed when coming from android dateDialog...
    return createLocalDateFromValues(year, realMonth, day)
}

fun createLocalDateFromValues(year: Int, month: Int, day: Int) : LocalDate {
    return LocalDate.of(year, month, day)
}

fun getFormattedDate(date: LocalDate) : String {
    return date.format(formatDateShort)
}

fun getFormattedTime(time: LocalTime) : String {
    return time.format(formatTime)
}

fun getFormattedTime(time: OffsetDateTime) : String {
    return time.format(formatTime)
}

fun epochSecondToHourOfDay(sec: Float): String {
    val zone = OffsetDateTime.now().offset
    val time = LocalDateTime.ofEpochSecond(sec.roundToLong(), 0, zone).toLocalTime()
    return getFormattedTime(time)
}

fun epochSecondToDate(sec: Float): String {
    val zone = OffsetDateTime.now().offset
    val date = LocalDateTime.ofEpochSecond(sec.roundToLong(), 0, zone).toLocalDate()
    return date.toStandardFormat(short = true)
}