package com.example.chronoboss.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimeDatabaseDao {

    @Insert
    suspend fun insert(day: TimeTracking)

    @Update
    suspend fun update(day: TimeTracking)

    @Query("SELECT * from daily_time_tracking WHERE dayId = :key")
    suspend fun get(key: Long): TimeTracking?

    @Query("DELETE FROM daily_time_tracking")
    suspend fun clear()

    @Query("SELECT * FROM daily_time_tracking ORDER BY dayId DESC LIMIT 1")
    suspend fun getToday(): TimeTracking?

    @Query("SELECT * FROM daily_time_tracking ORDER BY dayId DESC LIMIT 7")
    suspend fun getWeek(): TimeTracking?

    @Query("SELECT * FROM daily_time_tracking ORDER BY dayId DESC")
    fun getAll(): LiveData<List<TimeTracking>>
}




