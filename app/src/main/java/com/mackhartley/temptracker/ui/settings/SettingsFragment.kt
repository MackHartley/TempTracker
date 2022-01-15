package com.mackhartley.temptracker.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mackhartley.temptracker.databinding.FragmentSettingsBinding
import com.mackhartley.temptracker.getAppComponent
import javax.inject.Inject

class SettingsFragment : Fragment() {

    var binding: FragmentSettingsBinding? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SettingsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, modelFactory)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding = newBinding
        return newBinding.root
    }

}