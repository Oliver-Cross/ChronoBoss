package com.example.chronoboss.statsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.chronoboss.database.DayDao

class StatsViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {


    private val allDays = database.readAllData()
    private val todayData = database.getToday()



    val todayUsageString = Transformations.map(todayData) {(it.timeWasted.toFloat())}

}
