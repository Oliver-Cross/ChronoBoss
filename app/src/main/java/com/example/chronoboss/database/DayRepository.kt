package com.example.chronoboss.database

import androidx.lifecycle.LiveData

class DayRepository(private val dayDao: DayDao) {


    val readAllData: LiveData<List<Day>> = dayDao.readAllData()

    suspend fun addDay(day: Day) {
        dayDao.addDay(day)
    }

}