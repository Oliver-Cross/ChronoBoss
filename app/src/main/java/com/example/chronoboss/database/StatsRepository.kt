package com.example.chronoboss.database

import androidx.lifecycle.LiveData
import com.example.chronoboss.database.Stats
import com.example.chronoboss.database.StatsDao

class StatsRepository(private val statsDao: StatsDao) {


    val readAllData: LiveData<List<Stats>> = statsDao.readAllData()

    suspend fun addStats(stats: Stats) {
        statsDao.addStats(stats)
    }

}