package com.mackhartley.temptracker

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.mackhartley.temptracker.di.AppComponent

fun Fragment.getAppComponent(): AppComponent {
    val application = this.requireActivity().application
    return (application as App).appComponent
}

fun Fragment.findNavController(): NavController = NavHostFragment.findNavController(this)

fun Fragment.navigateTo(action: NavDirections) {
    findNavController().navigate(action)
}