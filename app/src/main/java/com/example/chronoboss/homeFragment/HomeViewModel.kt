package com.example.chronoboss.homeFragment

import android.app.Application
import androidx.lifecycle.*
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDao
import com.example.chronoboss.database.DayDatabase
import com.example.chronoboss.database.DayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {

    private var today = MutableLiveData<Day?>()
    private val repository: DayRepository


    private val allDays = database.readAllData()
    private val todayData = database.getToday()



    val todayUsageString = Transformations.map(todayData) {(it.timeLimit - it.timeWasted).toString()}
    val todayLimitString = Transformations.map(todayData) {it.timeLimit.toString()}


    val percentageLong = Transformations.map(todayData) {(100 * (it.timeWasted.toFloat() / it.timeLimit.toFloat())).toInt()}
    val packageName = Transformations.map(todayData) {it.application}


    //First executed when day view model is called
    init {
        val dayDao = DayDatabase.getDatabase(application).dayDao()
        repository = DayRepository(dayDao)
    }

    fun addDay(day: Day) {
        // Runs this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDay(day)
        }
    }


}

