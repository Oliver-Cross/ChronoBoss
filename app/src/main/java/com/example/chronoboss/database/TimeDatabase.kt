package com.example.chronoboss.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimeTracking::class], version = 1, exportSchema = false)
abstract class TimeDatabase : RoomDatabase() {

    abstract val timeDatabaseDao: TimeDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TimeDatabase? = null

        fun getInstance(context: Context): TimeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimeDatabase::class.java,
                        "time_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
