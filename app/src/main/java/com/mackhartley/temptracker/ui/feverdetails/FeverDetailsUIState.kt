package com.mackhartley.temptracker.ui.feverdetails

import com.mackhartley.temptracker.data.models.TempLog

data class FeverDetailsUIState(
    val temps: List<TempLog>
)