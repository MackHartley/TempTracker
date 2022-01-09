package com.mackhartley.temptracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "fever")
class Fever (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val dateTime: OffsetDateTime,
    val name: String
) {
    companion object {
        fun newInstance(name: String) = Fever(
            id = 0,
            dateTime = OffsetDateTime.now(),
            name = name
        )
    }
}