package com.example.chronoboss.homeFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDao
import com.example.chronoboss.formatDays
import kotlinx.coroutines.launch

class HomeViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {

    private var today = MutableLiveData<Day?>()
    private val allDays = database.readAllData()
    val dayStrings = Transformations.map(allDays) { allDays ->
        formatDays(allDays, application.resources)
    }

    /*
    init {
        initializeToday()
    }

    private fun initializeToday() {
        // For testing launches in view model scope, but in alpha, may need a different
        // scope based on background services.
        viewModelScope.launch {
            today.value = getTodayFromDatabase()
        }
    }


    private suspend fun getTodayFromDatabase(): Day? {
        var day = database.getToday()
        // Some logic to test if a day is made or not.
        if (day?.endTimeMilli != day?.startTimeMilli) {
            day = null
        }
        return day
    }

    //Testing
    fun onStartTracking() {

    */

    }

