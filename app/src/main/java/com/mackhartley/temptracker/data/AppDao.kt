package com.mackhartley.temptracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.data.models.TempLog
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFever(fever: Fever)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemperature(tempLog: TempLog)

    @Update
    suspend fun update(tempLog: TempLog)

    @Query("SELECT * FROM fever ORDER BY datetime(dateCreated) DESC")
    fun getAllFevers(): Flow<List<Fever>>

    @Query("SELECT * FROM temp_log WHERE parentId=:feverId ORDER BY datetime(dateCreated) DESC")
    fun getAllTempsForFever(feverId: Int): Flow<List<TempLog>>
}