package com.mackhartley.temptracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "fever")
data class Fever (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val dateCreated: OffsetDateTime,
    val name: String
) {
    companion object {
        fun newInstance(name: String) = Fever(
            id = 0,
            dateCreated = OffsetDateTime.now(),
            name = name
        )
    }
}