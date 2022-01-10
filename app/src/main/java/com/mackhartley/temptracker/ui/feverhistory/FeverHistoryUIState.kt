package com.mackhartley.temptracker.ui.feverhistory

import com.mackhartley.temptracker.data.models.TempLog

data class FeverHistoryUIState(
    val temps: List<TempLog>
)