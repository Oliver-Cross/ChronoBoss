package com.example.chronoboss.database

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * The interface between fragments and the room database.
 */
@Dao
interface DayDao {

    //Fixed error by removing suspend
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDay(day: Day)

    @Update
    fun update(day: Day)

    //Returns a list of day, which is a list of each row in the table. Wrapped in livedata, which allows automatic updating of UI.
    @Query("SELECT * FROM day_table ORDER BY dayId DESC")
    fun readAllData(): LiveData<List<Day>>


    @Query("SELECT * FROM day_table ORDER BY dayId DESC LIMIT 1")
    fun getToday(): LiveData<Day>

    @Query("SELECT * FROM day_table ORDER BY dayId DESC LIMIT 1")
    fun getTodayDay(): Day

    @Query("SELECT SUM(timeLimit - timeWasted) from day_table as time_saved")
    fun getTotalTimeSaved(): LiveData<Int>

}