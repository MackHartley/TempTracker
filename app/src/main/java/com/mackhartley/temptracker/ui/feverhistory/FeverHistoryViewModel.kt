package com.mackhartley.temptracker.ui.feverhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.data.TemperaturesRepo
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class FeverHistoryViewModel @Inject constructor(
    private val temperaturesRepo: TemperaturesRepo
) : ViewModel() {

    private val _uiState = MutableLiveData<FeverHistoryUIState>()
    val uiState: LiveData<FeverHistoryUIState>
        get() = _uiState

    fun retrieveTemps(feverId: Int) {
        viewModelScope.launch {
            temperaturesRepo.retrieveTemps(feverId).collect {
                _uiState.value = FeverHistoryUIState(it)
            }
        }
    }

}