package com.mackhartley.temptracker.ui.fevers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.databinding.FragmentFeversBinding
import com.mackhartley.temptracker.getAppComponent
import com.mackhartley.temptracker.navigateTo
import com.mackhartley.temptracker.utils.exhaustive
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class FeversFragment : Fragment(), FeversAdapter.FeverItemClickListener {

    var binding: FragmentFeversBinding? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: FeversViewModel
    lateinit var feversAdapter: FeversAdapter
    lateinit var feversRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, modelFactory)[FeversViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentFeversBinding.inflate(inflater, container, false)
        binding = newBinding
        initRV()
        setHasOptionsMenu(true)
        return newBinding.root
    }

    private fun initRV() {
        binding?.let {
            with(it.feversRv) {
                feversRecyclerView = this
                val feversLLM = LinearLayoutManager(activity)
                layoutManager = feversLLM
                feversAdapter = FeversAdapter(this@FeversFragment).also {
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

        viewModel.retrieveFevers()
        binding?.let {
            it.addFeverFab.setOnClickListener {
                viewModel.addFever()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
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
            viewModel.uiEvent.collect { newUiEvent ->
                when (newUiEvent) {
                    FeversUIEvent.NavigateToAddFeverUI -> {
                        val action = FeversFragmentDirections.actionFeverFragmentToAddFeverDialog()
                        navigateTo(action)
                    }
                    is FeversUIEvent.NavigateToFeverDetailsUI -> {
                        val action = FeversFragmentDirections.actionFeverFragmentToFeverDetailsFragment(newUiEvent.feverId)
                        navigateTo(action)
                    }
                    FeversUIEvent.NavigateToSettings -> {
                        val action = FeversFragmentDirections.actionFeverFragmentToSettingsFragment()
                        navigateTo(action)
                    }
                }.exhaustive
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fevers_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.settings -> viewModel.settingsIconClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun itemClicked(feverId: Int) {
        viewModel.feverClicked(feverId)
    }
}