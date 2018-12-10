package com.pjl.kyubee.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.pjl.kyubee.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}