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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mackhartley.temptracker.data.FeversRepo
import com.mackhartley.temptracker.databinding.FragmentFeversBinding
import com.mackhartley.temptracker.findNavController
import com.mackhartley.temptracker.getAppComponent
import com.mackhartley.temptracker.navigateTo
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class FeversFragment : Fragment() {

    var binding: FragmentFeversBinding? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var feversViewModel: FeversViewModel
    lateinit var feversAdapter: FeversListAdapter
    lateinit var feversRecyclerView: RecyclerView

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
        initRV()
        return newBinding.root
    }

    private fun initRV() {
        binding?.let {
            with(it.feversRv) {
                feversRecyclerView = this
                val feversLLM = LinearLayoutManager(activity)
                layoutManager = feversLLM
                feversAdapter = FeversListAdapter().also {
                    // REF: https://stackoverflow.com/questions/53248736/listadapter-submitlist-how-to-scroll-to-beginning
                    it.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            feversLLM.scrollToPositionWithOffset(positionStart, 0)
                        }
                    })
                }
                adapter = feversAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        feversViewModel.retrieveFevers()
        binding?.let {
            it.addFeverFab.setOnClickListener {
                feversViewModel.addFever()
            }
        }

        feversViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FeversUIState.Content -> {
                    binding?.let {
                        feversAdapter.submitList(state.fevers)
                    }
                }
                FeversUIState.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                FeversUIState.Loading -> {
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            feversViewModel.uiEvent.collect { newUiState ->
                when (newUiState) {
                    FeversUIEvent.NavigateToAddFeverUI -> {
                        val action: NavDirections = FeversFragmentDirections.actionFeverFragmentToAddFeverDialog()
                        navigateTo(action)
                    }
                }
            }
        }


    }
}