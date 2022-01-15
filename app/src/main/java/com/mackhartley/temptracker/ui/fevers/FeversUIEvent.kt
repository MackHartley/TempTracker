package com.mackhartley.temptracker.ui.fevers

sealed class FeversUIEvent {
    object NavigateToAddFeverUI : FeversUIEvent()
    data class NavigateToFeverDetailsUI(
        val feverId: Int
    ) : FeversUIEvent()
    object NavigateToSettings : FeversUIEvent()
}