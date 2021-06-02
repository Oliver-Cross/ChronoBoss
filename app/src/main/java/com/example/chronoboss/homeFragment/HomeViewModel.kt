package com.example.chronoboss.homeFragment

import android.app.Application
import androidx.lifecycle.*
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDao
import com.example.chronoboss.database.DayDatabase
import com.example.chronoboss.database.DayRepository
import com.example.chronoboss.formatDays
import com.example.chronoboss.formatPercentage
import com.example.chronoboss.formatTodayLimit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {

    private var today = MutableLiveData<Day?>()


    private val readAllData: LiveData<List<Day>>
    private val repository: DayRepository



    private val allDays = database.readAllData()
    private val todayData = database.getToday()

    /*
    val todayUsageString = Transformations.map(todayData) { todayData ->
        formatTodayUsage(todayData, application.resources)
    }


     */


    val todayUsageString = Transformations.map(todayData) {it.timeWasted.toString()}

    val todayLimitString = Transformations.map(todayData) { todayData ->
        formatTodayLimit(todayData, application.resources)
    }

    /*
    private val intLimitData = Transformations.map(todayLimitString) {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }
    */


    /*

    private val intUsageData = Transformations.map(todayUsageString) {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

     */



    val percentageLong = Transformations.map(todayData) {(100 * (it.timeWasted.toFloat() / it.timeLimit.toFloat())).toInt()}

    val percentageString = Transformations.map(todayData) { todayData ->
        formatPercentage(todayData, application.resources)
    }








    // Live data merger end


    val dayStrings = Transformations.map(allDays) { allDays ->
        formatDays(allDays, application.resources)
    }


    //First executed when day view model is called
    init {
        val dayDao = DayDatabase.getDatabase(application).dayDao()
        repository = DayRepository(dayDao)
        readAllData = repository.readAllData
    }

    fun addDay(day: Day) {
        // Runs this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDay(day)
        }
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

