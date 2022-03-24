package com.github.jw3.tl.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.github.jw3.tl.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
