package com.mackhartley.temptracker.data

import com.mackhartley.temptracker.data.models.Fever
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeversRepo @Inject constructor(
    private val appDao: AppDao
) {
    suspend fun addFever(fever: Fever) {
        appDao.insertTempCollection(fever)
    }

    suspend fun getAllFevers(): List<Fever> {
        return appDao.getAllFevers()
    }
}