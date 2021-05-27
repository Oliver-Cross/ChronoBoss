package com.example.chronoboss.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayDao {

    //Fixed error by removing suspend
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDay(day: Day)

    //Returns a list of day, which is a list of each row in the table. Wrapped in livedata, which allows automatic updating of UI.
    @Query("SELECT * FROM day_table ORDER BY dayId DESC")
    fun readAllData(): LiveData<List<Day>>


}