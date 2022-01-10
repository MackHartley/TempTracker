package com.mackhartley.temptracker.ui.feverdetails

sealed class FeverDetailsUIEvent {
    data class NavigateToFeverHistoryUI(
        val feverId: Int
    ) : FeverDetailsUIEvent()
    data class NavigateToAddEditTempUI(
        val feverId: Int
    ) : FeverDetailsUIEvent()
}