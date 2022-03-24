package com.github.jw3.tl.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.github.jw3.tl.prefs.CamPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
object CamModule {
    @Provides
    fun providePrefs(@ApplicationContext applicationContext: Context): CamPrefs {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return CamPrefs.newInstance(
            prefs.getString("camera_url", "localhost")!!,
            prefs.getString("camera_user", "admin")!!,
            prefs.getString("camera_pass", "admin")?.toCharArray()!!
        )
    }
}
