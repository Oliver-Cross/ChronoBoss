package com.example.chronoboss.statsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Links progress graph to the UI.
 */
class StatsViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {


    val allDays = database.readAllData()
    private val todayData = database.getToday()
    val todayWasted = Transformations.map(todayData) {
        it.timeWasted.toFloat()
    }




}
