package com.example.chronoboss.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stats::class], version = 1, exportSchema = false)
abstract class StatsDatabase: RoomDatabase() {

    abstract fun statsDao(): StatsDao

    // Everything made in the companion object will be visible to other classes
    companion object{
        //Make the dayDatabase a singleton class
        @Volatile
        private var INSTANCE: StatsDatabase? = null

        fun getDatabase(context: Context): StatsDatabase {
            //This code returns a instance of the database if it exists. Otherwise it creates one.
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StatsDatabase::class.java,
                    "stats_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }


}