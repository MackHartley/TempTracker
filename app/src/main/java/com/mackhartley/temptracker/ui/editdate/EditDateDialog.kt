package com.mackhartley.temptracker.ui.editdate

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.ui.core.BaseDialogFragment
import com.mackhartley.temptracker.utils.createLocalDateFromDatePickerCallback
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.util.*

class EditDateDialog : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    override val fragTag: String = "EditDateDialog"

    private var listener: DataReturnContract? = null
    private var requestCode: Int = DEFAULT_REQUEST_CODE

    companion object {
        private const val DEFAULT_REQUEST_CODE = -1
        private const val REQUEST_CODE_PARAM = "REQUEST_CODE_PARAM"
        private const val YEAR = "YEAR"
        private const val MONTH = "MONTH"
        private const val DAY = "DAY"

        fun newInstance(initialDate: LocalDate, requestCode: Int = DEFAULT_REQUEST_CODE) =
            EditDateDialog().apply {
                arguments = Bundle().apply {
                    putInt(YEAR, initialDate.year)
                    putInt(MONTH, initialDate.month.ordinal)
                    putInt(DAY, initialDate.dayOfMonth)
                    putInt(REQUEST_CODE_PARAM, requestCode)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val targetFrag = targetFragment
            if (targetFrag != null) {
                listener = (targetFrag as DataReturnContract)
                Timber.d("No targetFragment found for DatePickerDialog. Checking Activity for DataReturnContract")
            } else {
                listener = (activity as DataReturnContract)
            }
        } catch (e: Exception) {
            Timber.e(e, ("A targetFragment or activity must implement ${this.javaClass.simpleName} DataReturnContract"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activityContext = requireContext()

        val calendar = Calendar.getInstance()
        val curYear = calendar.get(Calendar.YEAR)
        val curMonth = calendar.get(Calendar.MONTH)
        val curDay = calendar.get(Calendar.DAY_OF_MONTH)

        val year = arguments?.getInt(YEAR) ?: curYear
        val month = arguments?.getInt(MONTH) ?: curMonth
        val day = arguments?.getInt(DAY) ?: curDay
        requestCode = arguments?.getInt(REQUEST_CODE_PARAM) ?: DEFAULT_REQUEST_CODE

        return DatePickerDialog(
            activityContext,
            R.style.PickerDialogTheme,
            (this as DatePickerDialog.OnDateSetListener),
            year,
            month,
            day
        )
    }

    interface DataReturnContract {
        fun onEasyDatePickerResult(chosenDate: LocalDate, requestCode: Int)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = createLocalDateFromDatePickerCallback(year, month, dayOfMonth)
        if (listener == null) Timber.e(Exception("NullListenerException"), "Listener was null when trying to call onDatePickerResult() from ${this.javaClass.simpleName}")
        listener?.onEasyDatePickerResult(date, requestCode)
    }
}