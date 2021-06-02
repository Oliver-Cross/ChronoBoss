package com.example.chronoboss.progressFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.chronoboss.database.DayDao

class ProgressViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {


    private val allDays = database.readAllData()
    val timeSavedLive = database.getTotalTimeSaved()
    val timeSaved = Transformations.map(timeSavedLive) {
        it.toString()
    }


}
