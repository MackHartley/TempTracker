package com.mackhartley.temptracker.data

import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromDate(date: OffsetDateTime): String {
        return date.toDBFormattedString()
    }

    @TypeConverter
    fun toDate(dateString: String): OffsetDateTime {
        return dateString.toOffsetDateTimeFromDBString()
    }
}

// Used for DB
val isoFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

fun OffsetDateTime.toDBFormattedString() : String {
    return this.format(isoFormatter)
}

fun String.toOffsetDateTimeFromDBString() : OffsetDateTime {
    return isoFormatter.parse(this, OffsetDateTime::from)
}