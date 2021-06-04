package com.example.chronoboss.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Database interface that fragments use instead of binding.
 * All queries are done in a background thread as a coroutine to improve response time.
 */
class DayViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Day>>
    private val repository: DayRepository
    private val today: LiveData<Day>

    //First executed when day view model is called
    init {
        val dayDao = DayDatabase.getDatabase(application).dayDao()
        repository = DayRepository(dayDao)
        readAllData = repository.readAllData
        today = repository.todayData
    }

    fun addDay(day: Day) {
        // Runs this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDay(day)
        }
    }

    fun readAllData(): LiveData<List<Day>> {
        return readAllData
    }

    fun readToday(): LiveData<Day> {
        return today
    }

    fun updateTodayTimeWasted(timeWasted: Long) {
        // Runs this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            val todayDay = repository.getTodayDay()
            val updatedDay = Day(todayDay.dayId, timeWasted, todayDay.timeLimit, todayDay.application)
            repository.updateDay(updatedDay)
        }
    }

    fun updateTimeLimit(timeLimit: Long) {
        // Runs this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            val todayDay = repository.getTodayDay()
            val updatedDay = Day(todayDay.dayId, todayDay.timeWasted, timeLimit, todayDay.application)
            repository.updateDay(updatedDay)
        }
    }

    fun setPackageName(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val todayDay = repository.getTodayDay()
            val updatedDay = Day(todayDay.dayId, todayDay.timeWasted, todayDay.timeLimit, packageName)
            repository.updateDay(updatedDay)
        }
    }


}