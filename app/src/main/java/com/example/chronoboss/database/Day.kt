package com.example.chronoboss.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The Day room table. It records data based on days.
 * Currently creates one day row and updates it throughout the day.
 * Extra days will be added in a future version.
 */
@Entity(tableName = "day_table")
data class Day (
    //Starting with dayId to get it to work. Changing it to a date representing a single day later.
    @PrimaryKey(autoGenerate = true)
    val dayId: Int,
    val timeWasted: Long,
    val timeLimit: Long,
    val application: String,
        )
