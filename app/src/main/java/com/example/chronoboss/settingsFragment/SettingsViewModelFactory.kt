package com.example.chronoboss.settingsFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.database.DayDao
import com.example.chronoboss.statsFragment.StatsViewModel

/**
 * Creates the settings view model
 */
class SettingsViewModelFactory(
private val dataSource: DayDao,
private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}