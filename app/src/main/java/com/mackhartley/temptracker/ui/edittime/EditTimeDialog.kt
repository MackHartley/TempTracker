package com.mackhartley.temptracker.ui.edittime

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.FragmentActivity
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.ui.core.BaseDialogFragment
import org.threeten.bp.LocalTime
import timber.log.Timber
import java.util.*

class EditTimeDialog :
    BaseDialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    override val fragTag: String = "EditTimeDialog"

    private var listener: DataReturnContract? = null
    private var requestCode: Int = DEFAULT_REQUEST_CODE

    companion object {
        private const val DEFAULT_REQUEST_CODE = -1
        private const val REQUEST_CODE_PARAM = "REQUEST_CODE_PARAM"
        private const val HOUR = "HOUR"
        private const val MINUTE = "MINUTE"

        fun newInstance(time: LocalTime, requestCode: Int = DEFAULT_REQUEST_CODE) =
            EditTimeDialog().apply {
                arguments = Bundle().apply {
                    putInt(HOUR, time.hour)
                    putInt(MINUTE, time.minute)
                    putInt(REQUEST_CODE_PARAM, requestCode)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = (targetFragment as DataReturnContract)
        } catch (e: Exception) {
            Timber.e(e, ("A targetFragment or activity must implement ${this.javaClass.simpleName} DataReturnContract"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activityContext = requireContext()

        val calendar = Calendar.getInstance()
        val curHour = calendar.get(Calendar.HOUR_OF_DAY)
        val curMinute = calendar.get(Calendar.MINUTE)

        val hour = arguments?.getInt(HOUR) ?: curHour
        val minute = arguments?.getInt(MINUTE) ?: curMinute

        return TimePickerDialog(
            activityContext,
            R.style.PickerDialogTheme,
            (this as TimePickerDialog.OnTimeSetListener),
            hour,
            minute,
            DateFormat.is24HourFormat(activityContext)
        )
    }

    interface DataReturnContract {
        fun onEasyTimePickerResult(chosenTime: LocalTime, requestCode: Int)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val time = LocalTime.of(hourOfDay, minute, 0, 0)
        if (listener == null) Timber.e(Exception("NullListenerException"), "Listener was null when trying to call onTimePickerResult() from ${this.javaClass.simpleName}")
        listener?.onEasyTimePickerResult(time, requestCode)
    }
}