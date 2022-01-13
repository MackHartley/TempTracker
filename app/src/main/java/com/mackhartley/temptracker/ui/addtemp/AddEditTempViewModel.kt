package com.mackhartley.temptracker.ui.addtemp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.data.TempsRepo
import com.mackhartley.temptracker.data.models.TempLog
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class AddEditTempViewModel @Inject constructor(
    private val tempsRepo: TempsRepo
) : ViewModel() {

    fun addNewTemp(
        feverId: Int,
        dateTime: OffsetDateTime,
        temp: Double
    ) {
        val newTempLog = TempLog.newInstance(feverId, dateTime, temp)
        viewModelScope.launch {
            tempsRepo.addNewTemp(newTempLog)
        }
    }

    fun updateTemp(
        prevId: Int,
        feverId: Int,
        dateTime: OffsetDateTime,
        temp: Double
    ) {
        val tempLogToUpdate = TempLog(
            id = prevId,
            parentId = feverId,
            dateCreated = dateTime,
            temp = temp
        )
        viewModelScope.launch {
            tempsRepo.updateTemp(tempLogToUpdate)
        }
    }
}