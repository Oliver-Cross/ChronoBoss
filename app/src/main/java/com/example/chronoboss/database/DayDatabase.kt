package com.example.chronoboss.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * This creates the database as a singleton class.
 * This means that only one instance of the database is used for the whole app.
 */
@Database(entities = [Day::class], version = 1, exportSchema = false)
abstract class DayDatabase: RoomDatabase() {

    abstract fun dayDao(): DayDao

    companion object{
        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getDatabase(context: Context): DayDatabase {
            //This code returns a instance of the database if it exists. Otherwise it creates one.
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DayDatabase::class.java,
                    "day_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

    }


}