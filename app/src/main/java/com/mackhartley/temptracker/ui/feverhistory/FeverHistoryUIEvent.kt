package com.mackhartley.temptracker.ui.feverhistory

sealed class FeverHistoryUIEvent {
    data class NavigateToAddEditTempUI(
        val feverId: Int
    ) : FeverHistoryUIEvent()
}