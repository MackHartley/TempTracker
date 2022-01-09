package com.mackhartley.temptracker

import androidx.fragment.app.Fragment
import com.mackhartley.temptracker.di.AppComponent

fun Fragment.getAppComponent(): AppComponent {
    val application = this.requireActivity().application
    return (application as App).appComponent
}