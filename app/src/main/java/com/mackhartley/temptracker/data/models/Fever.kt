package com.mackhartley.temptracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "fever")
class Fever (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val dateTime: OffsetDateTime,
    val name: String
)