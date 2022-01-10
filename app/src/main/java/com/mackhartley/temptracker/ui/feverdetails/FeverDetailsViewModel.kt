package com.mackhartley.temptracker.ui.feverdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeverDetailsViewModel @Inject constructor(

) : ViewModel() {

//    private val _uiState = MutableLiveData<>()
//    val uiState: LiveData<>
//        get() = _uiState

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

}