package com.example.chronoboss.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.chronoboss.database.Stats
import com.example.chronoboss.database.StatsDatabase
import com.example.chronoboss.database.StatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsViewModel(application: Application): AndroidViewModel(application) {

    val topPackageName: String?
    val readAllData: LiveData<List<Stats>>
    private val repository: StatsRepository

    //First executed when day view model is called
    init {
        val statsDao = StatsDatabase.getDatabase(application).statsDao()
        repository = StatsRepository(statsDao)
        readAllData = repository.readAllData
    }

    fun addStats(stats: Stats) {
        // Runs this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStats(stats)
        }
    }
}