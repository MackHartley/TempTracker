package com.mackhartley.temptracker.data

import com.mackhartley.temptracker.data.models.TempLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TempsRepo @Inject constructor(
    private val appDao: AppDao
) {
    fun retrieveTemps(feverId: Int): Flow<List<TempLog>> {
        return appDao.getAllTempsForFever(feverId)
    }

    suspend fun addNewTemp(tempLog: TempLog) {
        appDao.insertTemperature(tempLog)
    }
}