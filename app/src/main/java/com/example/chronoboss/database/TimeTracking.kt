package com.example.chronoboss.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat


@Entity(tableName = "daily_time_tracking")
data class TimeTracking(
    @PrimaryKey(autoGenerate = true)
    var dayId: Long,
    @ColumnInfo(name = "quality_rating")
    var word: String
)