package com.mackhartley.temptracker.ui.fevers

import com.mackhartley.temptracker.data.models.Fever

sealed class FeversUIState {
    object Loading : FeversUIState()
    object Error : FeversUIState()
    data class Content(
        val fevers: List<Fever>
    ) : FeversUIState()
}