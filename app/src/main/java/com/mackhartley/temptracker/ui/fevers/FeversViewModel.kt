package com.mackhartley.temptracker.ui.fevers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.usecases.RetrieveFeversUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeversViewModel @Inject constructor(
    private val retrieveFeversUseCase: RetrieveFeversUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<FeversUIState>()
    val uiState: LiveData<FeversUIState>
        get() = _uiState

    private val _uiEvent = Channel<FeversUIEvent>()
    val uiEvent: Flow<FeversUIEvent>
        get() = _uiEvent.receiveAsFlow()

    fun retrieveFevers() {
        viewModelScope.launch {
            try {
                _uiState.value = FeversUIState.Loading
                val fevers: List<Fever> = retrieveFeversUseCase.invoke()
                _uiState.value = fevers.toContent()
            } catch (exception: Exception) {
                _uiState.value = FeversUIState.Error
            }
        }
    }

    fun addFever() {
        viewModelScope.launch {
            _uiEvent.send(FeversUIEvent.NavigateToAddFeverUI)
        }
    }
}