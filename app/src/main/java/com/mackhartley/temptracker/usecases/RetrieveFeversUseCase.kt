package com.mackhartley.temptracker.usecases

import com.mackhartley.temptracker.data.FeversRepo
import com.mackhartley.temptracker.data.models.Fever
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrieveFeversUseCase @Inject constructor(
    private val feversRepo: FeversRepo
) {
    suspend fun invoke(): List<Fever> {
        return feversRepo.getAllFevers()
    }
}