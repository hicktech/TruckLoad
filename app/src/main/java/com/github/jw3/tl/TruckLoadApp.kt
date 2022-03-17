package com.github.jw3.tl

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TruckLoadApp: Application() {
    override fun onCreate() {
        super.onCreate()
        applicationContext.deleteDatabase("LoadsDb")
    }
}
