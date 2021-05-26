package com.example.chronoboss.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

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

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM daily_time_tracking ORDER BY dayId ASC")
    fun getAlphabetizedWords(): Flow<List<TimeTracking>>

    @Query("DELETE FROM daily_time_tracking")
    suspend fun deleteAll()
}




