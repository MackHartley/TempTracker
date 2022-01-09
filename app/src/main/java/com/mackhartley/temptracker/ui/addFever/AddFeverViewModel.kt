package com.mackhartley.temptracker.ui.addFever

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.data.FeversRepo
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.utils.toStandardFormat
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class AddFeverViewModel @Inject constructor(
    private val feversRepo: FeversRepo
) : ViewModel() {

    fun getDefaultFeverName(): String {
        val date = OffsetDateTime.now()
        return date.toStandardFormat(true)
    }

    fun addFever(name: String) {
        viewModelScope.launch {
            feversRepo.addFever(
                Fever.newInstance(name)
            )
        }
    }

}