package com.mackhartley.temptracker.ui.fevers

sealed class FeversUIEvent {
    object NavigateToAddFeverUI : FeversUIEvent()
}