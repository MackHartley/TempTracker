package com.mackhartley.temptracker.ui.addtemp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.databinding.DialogAddEditTempBinding
import com.mackhartley.temptracker.databinding.DialogAddFeverBinding
import com.mackhartley.temptracker.getAppComponent
import com.mackhartley.temptracker.ui.addfever.AddFeverViewModel
import javax.inject.Inject

class AddEditTempDialog : DialogFragment() {
    var binding: DialogAddEditTempBinding? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: AddEditTempViewModel

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

        return builder
            .setTitle(getString(R.string.record_temperature))
            .setPositiveButton(getString(R.string.add)) {_, _ -> }
            .setNegativeButton(getString(R.string.cancel), null)
            .setView(newBinding.root)
            .create()
    }
}