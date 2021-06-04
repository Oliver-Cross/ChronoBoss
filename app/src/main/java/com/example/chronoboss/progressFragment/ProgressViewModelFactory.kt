package com.example.chronoboss.progressFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.database.DayDao
import com.example.chronoboss.homeFragment.HomeViewModel


/**
 * Creates the progress view model.
 */
class ProgressViewModelFactory(
    private val dataSource: DayDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            return ProgressViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}