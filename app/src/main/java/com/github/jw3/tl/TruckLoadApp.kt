package com.github.jw3.tl

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TruckLoadApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // todo;; move this to a ui control
        //applicationContext.deleteDatabase("LoadsDb")
    }
}
