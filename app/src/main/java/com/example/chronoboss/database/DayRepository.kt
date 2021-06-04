package com.example.chronoboss.database

import androidx.lifecycle.LiveData

/**
 * An interface between the Dao and the view model.
 * Currently not essential, but used when network traffic is introduced in a later version.
 */
class DayRepository(private val dayDao: DayDao) {


    val readAllData: LiveData<List<Day>> = dayDao.readAllData()
    val todayData: LiveData<Day> = dayDao.getToday()

    suspend fun addDay(day: Day) {
        dayDao.addDay(day)
    }

    fun updateDay(day: Day) {
        dayDao.update(day)
    }

    suspend fun getTodayDay(): Day {
        return dayDao.getTodayDay()
    }

}