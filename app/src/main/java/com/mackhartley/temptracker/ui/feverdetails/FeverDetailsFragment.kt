package com.mackhartley.temptracker.ui.feverdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mackhartley.temptracker.databinding.FragmentFeverDetailsBinding
import com.mackhartley.temptracker.getAppComponent
import com.mackhartley.temptracker.navigateTo
import com.mackhartley.temptracker.utils.exhaustive
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class FeverDetailsFragment : Fragment() {

    var binding: FragmentFeverDetailsBinding? = null
    val args: FeverDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    lateinit var viewModel: FeverDetailsViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, modelFactory)[FeverDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentFeverDetailsBinding.inflate(inflater, container, false)
        with(newBinding) {
            viewHistoryBtn.setOnClickListener {
                viewModel.viewFeverHistory(args.feverId)
            }
            addTempFab.setOnClickListener {
                viewModel.addNewTemp(args.feverId)
            }
        }
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { newUiEvent ->
                when (newUiEvent) {
                    is FeverDetailsUIEvent.NavigateToFeverHistoryUI -> {
                        val action = FeverDetailsFragmentDirections.actionFeverDetailsFragmentToFeverHistoryFragment(newUiEvent.feverId)
                        navigateTo(action)
                    }
                    is FeverDetailsUIEvent.NavigateToAddEditTempUI -> {
                        val action = FeverDetailsFragmentDirections.actionFeverDetailsFragmentToAddEditTempDialog(newUiEvent.feverId)
                        navigateTo(action)
                    }
                }.exhaustive
            }
        }
    }
}