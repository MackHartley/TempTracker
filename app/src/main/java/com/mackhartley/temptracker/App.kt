package com.mackhartley.temptracker

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mackhartley.temptracker.di.AppComponent
import com.mackhartley.temptracker.di.DaggerAppComponent
import com.mackhartley.temptracker.utils.HyperlinkedDebugTree
import timber.log.Timber

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        AndroidThreeTen.init(this)
        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(HyperlinkedDebugTree())
//            firebaseCrashlytics.setCrashlyticsCollectionEnabled(false)
        } else {
//            Timber.plant(CrashReportingTree(firebaseCrashlytics))
//            firebaseCrashlytics.setCrashlyticsCollectionEnabled(true)
        }
    }
}