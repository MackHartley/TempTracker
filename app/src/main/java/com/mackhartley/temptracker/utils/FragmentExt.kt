package com.mackhartley.temptracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.mackhartley.temptracker.di.AppComponent
import com.mackhartley.temptracker.ui.core.BaseDialogFragment

fun Fragment.getAppComponent(): AppComponent {
    val application = this.requireActivity().application
    return (application as App).appComponent
}

fun Fragment.findNavController(): NavController = NavHostFragment.findNavController(this)

fun Fragment.navigateTo(action: NavDirections) {
    findNavController().navigate(action)
}

fun Fragment.showDialog(dialog: BaseDialogFragment) {
    val fragManager: FragmentManager = this.parentFragmentManager
    dialog.setTargetFragment(this, 0) // 0 is default/empty request code
    openDialogWithFragManager(dialog, fragManager)
}

private fun openDialogWithFragManager(dialog: BaseDialogFragment, fragmentManager: FragmentManager) {
    val dialogAlreadyOpen = fragmentManager.findFragmentByTag(dialog.fragTag) != null
    if (!dialogAlreadyOpen) dialog.show(fragmentManager, dialog.fragTag)
}