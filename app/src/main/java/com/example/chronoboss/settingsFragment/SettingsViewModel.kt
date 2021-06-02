package com.example.chronoboss.settingsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.chronoboss.database.DayDao

class SettingsViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {


    private val allDays = database.readAllData()

}
