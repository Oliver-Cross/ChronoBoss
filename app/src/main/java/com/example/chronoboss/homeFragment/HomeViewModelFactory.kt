package com.example.chronoboss.homeFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.database.DayDao
import com.example.chronoboss.database.DayViewModel

/**
 * Creates the view model as a single class. Required to set up the binding.
 */
class HomeViewModelFactory(
    private val dataSource: DayDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}