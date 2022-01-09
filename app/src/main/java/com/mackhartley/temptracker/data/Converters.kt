package com.mackhartley.temptracker.data

import androidx.room.TypeConverter
import com.mackhartley.temptracker.utils.toDBFormattedString
import com.mackhartley.temptracker.utils.toOffsetDateTimeFromDBString
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