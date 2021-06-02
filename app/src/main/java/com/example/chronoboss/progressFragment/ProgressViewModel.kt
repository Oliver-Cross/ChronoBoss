package com.example.chronoboss.progressFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chronoboss.database.DayDao

class ProgressViewModel (
    val database: DayDao,
    application: Application
): AndroidViewModel(application) {


    private val allDays = database.readAllData()
    val timeSaved = database.getTotalTimeSaved().toString()


}
