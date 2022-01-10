package com.mackhartley.temptracker.data

import com.mackhartley.temptracker.data.models.Fever
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeversRepo @Inject constructor(
    private val appDao: AppDao
) {
    suspend fun addFever(fever: Fever) {
        appDao.insertFever(fever)
    }

    fun getAllFevers(): Flow<List<Fever>> {
        return appDao.getAllFevers()
    }
}