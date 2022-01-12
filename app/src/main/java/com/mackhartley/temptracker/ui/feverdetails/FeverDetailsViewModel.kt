package com.mackhartley.temptracker.ui.feverdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.data.TempsRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeverDetailsViewModel @Inject constructor(
    private val tempsRepo: TempsRepo
) : ViewModel() {

    private val _uiState = MutableLiveData<FeverDetailsUIState>()
    val uiState: LiveData<FeverDetailsUIState>
        get() = _uiState

    private val _uiEvent = Channel<FeverDetailsUIEvent>()
    val uiEvent: Flow<FeverDetailsUIEvent>
        get() = _uiEvent.receiveAsFlow()

    fun viewFeverHistory(feverId: Int) {
        viewModelScope.launch {
            _uiEvent.send(FeverDetailsUIEvent.NavigateToFeverHistoryUI(feverId))
        }
    }

    fun addNewTemp(feverId: Int) {
        viewModelScope.launch {
            _uiEvent.send(FeverDetailsUIEvent.NavigateToAddEditTempUI(feverId))
        }
    }

    fun getTemps(feverId: Int) {
        viewModelScope.launch {
            tempsRepo.retrieveTemps(feverId).collect {
                _uiState.value = FeverDetailsUIState(it)
            }
        }
    }

}