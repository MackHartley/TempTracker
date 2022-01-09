package com.mackhartley.temptracker.ui.fevers

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mackhartley.temptracker.data.FeversRepo
import com.mackhartley.temptracker.databinding.FragmentFeversBinding
import com.mackhartley.temptracker.getAppComponent
import javax.inject.Inject

class FeversFragment : Fragment() {

    var binding: FragmentFeversBinding? = null


    @Inject lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var feversViewModel: FeversViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        feversViewModel = ViewModelProvider(this, modelFactory)[FeversViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentFeversBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.let {
            it.something.setOnClickListener {
                feversViewModel.retrieveFevers()
            }
        }

        feversViewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is FeversUIState.Content -> {
                    binding?.let { it.dummy.text = "testtingsdf" }
                }
                FeversUIState.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                FeversUIState.Loading -> {}
            }
        }

    }
}