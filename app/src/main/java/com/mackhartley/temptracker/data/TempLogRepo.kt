package com.mackhartley.temptracker.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TempLogRepo @Inject constructor(
    private val tempDao: TempDao
) {
    suspend fun insertTempCollection(tempCollection: TempCollection) {
        tempDao.insertTempCollection(tempCollection)
    }

    fun getAllTempCollections(): Flow<List<TempCollection>> {
        return tempDao.getAllTempCollections()
    }
}