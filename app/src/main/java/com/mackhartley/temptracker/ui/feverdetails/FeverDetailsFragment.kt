package com.mackhartley.temptracker.ui.feverdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mackhartley.temptracker.databinding.FragmentFeverDetailsBinding
import com.mackhartley.temptracker.getAppComponent

class FeverDetailsFragment : Fragment() {

    var binding: FragmentFeverDetailsBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentFeverDetailsBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }
}