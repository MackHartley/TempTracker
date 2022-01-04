package com.mackhartley.temptracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TempDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTempCollection(tempCollection: TempCollection)

    @Query("SELECT * FROM temp_collection ORDER BY datetime(dateTime) DESC")
    fun getAllTempCollections(): Flow<List<TempCollection>>
}