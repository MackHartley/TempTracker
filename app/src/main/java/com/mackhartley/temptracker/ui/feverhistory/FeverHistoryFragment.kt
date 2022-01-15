package com.mackhartley.temptracker.ui.feverhistory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mackhartley.temptracker.data.models.TempLog
import com.mackhartley.temptracker.databinding.FragmentFeverHistoryBinding
import com.mackhartley.temptracker.getAppComponent
import com.mackhartley.temptracker.navigateTo
import com.mackhartley.temptracker.ui.feverdetails.FeverDetailsFragmentArgs
import com.mackhartley.temptracker.utils.exhaustive
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FeverHistoryFragment : Fragment(), TempAdapter.TempItemClickListener {

    private var binding: FragmentFeverHistoryBinding? = null
    private val args: FeverDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: FeverHistoryViewModel
    lateinit var tempAdapter: TempAdapter
    lateinit var tempRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, modelFactory)[FeverHistoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentFeverHistoryBinding.inflate(inflater, container, false)
        binding = newBinding
        initRv()
        return newBinding.root
    }

    private fun initRv() {
        binding?.let {
            with(it.tempsRv) {
                tempRecyclerView = this
                val tempLLM = LinearLayoutManager(activity)
                layoutManager = tempLLM
                tempAdapter = TempAdapter(this@FeverHistoryFragment).also {
                    it.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            tempLLM.scrollToPositionWithOffset(positionStart, 0)
                        }
                    })
                }
                adapter = tempAdapter
            }
            it.addTempFab.setOnClickListener {
                viewModel.addNewTemp(args.feverId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding?.let {
                tempAdapter.submitList(state.temps)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { newUiEvent ->
                when (newUiEvent) {
                    is FeverHistoryUIEvent.NavigateToAddEditTempUI -> {
                        val action = FeverHistoryFragmentDirections.actionFeverHistoryFragmentToAddEditTempDialog(newUiEvent.feverId, null)
                        navigateTo(action)
                    }
                }.exhaustive
            }
        }

        viewModel.retrieveTemps(args.feverId)
    }

    override fun itemClicked(tempLog: TempLog) {
        val action = FeverHistoryFragmentDirections.actionFeverHistoryFragmentToAddEditTempDialog(args.feverId, tempLog)
        navigateTo(action)
    }
}