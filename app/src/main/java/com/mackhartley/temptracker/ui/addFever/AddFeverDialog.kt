package com.mackhartley.temptracker.ui.addFever

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.databinding.AddFeverDialogBinding
import com.mackhartley.temptracker.getAppComponent
import javax.inject.Inject

class AddFeverDialog : DialogFragment() {

    var binding: AddFeverDialogBinding? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: AddFeverViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, modelFactory)[AddFeverViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val builder = MaterialAlertDialogBuilder(context)

        val newBinding = AddFeverDialogBinding.inflate(LayoutInflater.from(context))
        binding = newBinding

        binding?.let {
            val dateString = viewModel.getFeverDateString()
            val defaultLabel = context.getString(viewModel.getDefaultFeverLabelRes())
            val defaultName = "$defaultLabel $dateString"
            it.feverName.setText(defaultName)
        }

        return builder
            .setTitle(getString(R.string.new_fever_record))
            .setPositiveButton(getString(R.string.add)) { _, _ -> addFever() }
            .setNegativeButton(getString(R.string.cancel), null)
            .setView(newBinding.root)
            .create()
    }

    fun addFever() {
        binding?.let {
            val newFeverName = it.feverName.text.toString() // Todo add some basic error handling with empty name
            viewModel.addFever(newFeverName)
        }
    }

}