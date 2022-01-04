package com.mackhartley.temptracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "temp_log")
data class TempLog(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val datetime: OffsetDateTime,
    val temp: Double
)