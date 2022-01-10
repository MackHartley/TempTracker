package com.mackhartley.temptracker.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(
    tableName = "temp_log",
    foreignKeys = [
        ForeignKey(
            entity = Fever::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TempLog(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(index = true)
    val parentId: Int,
    val dateCreated: OffsetDateTime,
    val temp: Double
)

// Need ref to a Fever entity (https://medium.com/android-news/android-architecture-components-room-relationships-bf473510c14a)
// Need to add a medicin category