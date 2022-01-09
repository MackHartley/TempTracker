package com.mackhartley.temptracker

import android.app.Application
import com.mackhartley.temptracker.di.AppComponent
import com.mackhartley.temptracker.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}