package com.ersinberkealemdaroglu.arackaskodegerlistesi

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InsureApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

    }

    companion object {
        lateinit var appContext: Context
    }

}