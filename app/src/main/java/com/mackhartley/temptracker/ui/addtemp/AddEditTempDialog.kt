package com.mackhartley.temptracker.ui.addtemp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.databinding.DialogAddEditTempBinding
import com.mackhartley.temptracker.databinding.DialogAddFeverBinding
import com.mackhartley.temptracker.getAppComponent
import com.mackhartley.temptracker.showDialog
import com.mackhartley.temptracker.ui.addfever.AddFeverViewModel
import com.mackhartley.temptracker.ui.editdate.EditDateDialog
import com.mackhartley.temptracker.ui.edittime.EditTimeDialog
import com.mackhartley.temptracker.ui.feverdetails.FeverDetailsFragmentArgs
import com.mackhartley.temptracker.utils.getFormattedDate
import com.mackhartley.temptracker.utils.getFormattedTime
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class AddEditTempDialog : DialogFragment(),
    EditDateDialog.DataReturnContract,
    EditTimeDialog.DataReturnContract {

    var binding: DialogAddEditTempBinding? = null

    private val args: FeverDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: AddEditTempViewModel

    private var chosenDate : LocalDate = LocalDate.now()
    private var chosenTime : LocalTime = LocalTime.now()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, modelFactory)[AddEditTempViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val builder = MaterialAlertDialogBuilder(context)

        val newBinding = DialogAddEditTempBinding.inflate(LayoutInflater.from(context))
        binding = newBinding

        newBinding.let {
            it.tempTimeField.setOnClickListener {
                val timePickerDialog = EditTimeDialog.newInstance(chosenTime)
                showDialog(timePickerDialog)
            }
            it.tempDateField.setOnClickListener {
                val datePickerDialog = EditDateDialog.newInstance(chosenDate)
                showDialog(datePickerDialog)
            }
        }

        initDateView(chosenDate)
        initTimeView(chosenTime)

        return builder
            .setTitle(getString(R.string.record_temperature))
            .setPositiveButton(getString(R.string.add)) {_, _ -> addNewTemp()}
            .setNegativeButton(getString(R.string.cancel), null)
            .setView(newBinding.root)
            .create()
    }

    private fun addNewTemp() {
        binding?.let {
            val temp = getProvidedTemp(it)
            val dateTime = getODT(chosenTime, chosenDate)
            if (temp != null) {
                viewModel.addNewTemp(args.feverId, dateTime, temp)
            }
        }
    }

    private fun getProvidedTemp(binding: DialogAddEditTempBinding): Double? {
        val numStr = binding.tempEt.text.toString()
        return try {
            numStr.toDouble()
        } catch (e: Exception) {
            null
        }
    }

    private fun getODT(time: LocalTime, date: LocalDate): OffsetDateTime {
        val zoneOffset = OffsetDateTime.now().offset
        return OffsetDateTime.of(date, time, zoneOffset)
    }

    private fun updateDate(date: LocalDate) {
        binding?.let { it.tempDateField.text = getFormattedDate(date) }
        chosenDate = date
    }

    private fun updateTime(time: LocalTime) {
        binding?.let { it.tempTimeField.text = getFormattedTime(time) }
        chosenTime = time
    }

    private fun initDateView(date: LocalDate) {
        updateDate(date)
    }

    private fun initTimeView(time: LocalTime) {
        updateTime(time)
    }

    override fun onEasyDatePickerResult(chosenDate: LocalDate, requestCode: Int) {
        updateDate(chosenDate)
    }

    override fun onEasyTimePickerResult(chosenTime: LocalTime, requestCode: Int) {
        updateTime(chosenTime)
    }
}