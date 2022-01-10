package com.mackhartley.temptracker.ui.addtemp

import androidx.lifecycle.ViewModel
import com.mackhartley.temptracker.data.TemperaturesRepo
import javax.inject.Inject

class AddEditTempViewModel @Inject constructor(
    private val temperaturesRepo: TemperaturesRepo
) : ViewModel() {

}