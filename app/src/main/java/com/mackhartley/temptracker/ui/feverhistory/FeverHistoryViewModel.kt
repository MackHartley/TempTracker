package com.mackhartley.temptracker.ui.feverhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.data.TempsRepo
import com.mackhartley.temptracker.ui.feverdetails.FeverDetailsUIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

class FeverHistoryViewModel @Inject constructor(
    private val tempsRepo: TempsRepo
) : ViewModel() {

    private val _uiState = MutableLiveData<FeverHistoryUIState>()
    val uiState: LiveData<FeverHistoryUIState>
        get() = _uiState

    private val _uiEvent = Channel<FeverHistoryUIEvent>()
    val uiEvent: Flow<FeverHistoryUIEvent>
        get() = _uiEvent.receiveAsFlow()

    fun retrieveTemps(feverId: Int) {
        viewModelScope.launch {
            tempsRepo.retrieveTemps(feverId).collect {
                _uiState.value = FeverHistoryUIState(it)
            }
        }
    }

    fun addNewTemp(feverId: Int) {
        viewModelScope.launch {
            _uiEvent.send(FeverHistoryUIEvent.NavigateToAddEditTempUI(feverId))
        }
    }
}