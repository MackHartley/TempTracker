package com.mackhartley.temptracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "temp_collection")
class TempCollection (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val dateTime: OffsetDateTime,
    val name: String
)