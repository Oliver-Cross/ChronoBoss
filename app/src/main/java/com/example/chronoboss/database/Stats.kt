package com.example.chronoboss.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats_table")
data class Stats(
    //Starting with dayId to get it to work. Changing it to a date representing a single day later.
    @PrimaryKey(autoGenerate = true)
    var timeInForeground: Long?,
    var topPackageName: String?

    )