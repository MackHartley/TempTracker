package com.mackhartley.temptracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mackhartley.temptracker.data.models.Fever
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTempCollection(fever: Fever)

    @Query("SELECT * FROM fever ORDER BY datetime(dateTime) DESC")
    fun getAllFevers(): Flow<List<Fever>>
}