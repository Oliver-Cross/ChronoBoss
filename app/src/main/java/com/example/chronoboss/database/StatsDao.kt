package com.example.chronoboss.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chronoboss.database.Stats

@Dao
interface StatsDao {

    //Fixed error by removing suspend
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addStats(stats: Stats)

    //Returns a list of day, which is a list of each row in the table. Wrapped in livedata, which allows automatic updating of UI.
    @Query("SELECT * FROM stats_table ORDER BY topPackageName DESC")
    fun readAllData(): LiveData<List<Stats>>


}