package com.mackhartley.temptracker.ui.addfever

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.data.FeversRepo
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.utils.toStandardFormat
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class AddFeverViewModel @Inject constructor(
    private val feversRepo: FeversRepo
) : ViewModel() {

    fun getFeverDateString(): String {
        val date = OffsetDateTime.now()
        return date.toStandardFormat(true)
    }

    fun getDefaultFeverLabelRes(): Int {
        return R.string.fever_on
    }

    fun addFever(name: String) {
        viewModelScope.launch {
            feversRepo.addFever(
                Fever.newInstance(name)
            )
        }
    }

}