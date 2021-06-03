package com.example.chronoboss

import android.app.usage.UsageStats

class QueryStatsConsts {
    var timeInForeground:Long? = null
    var topPckgeName:String? = null
    var topPckge:UsageStats? = null
    //var compareTime:Long? = null
    //var isLimitReached:Boolean = false
    var statsForDay:List<UsageStats>? = null
    //val messageLimit:String = "The limit you have set is less than the current time you have used"
    val messagePermission:String = "Access to usage data requires your permission"
    //var appToTrack:UsageStats? = null
    //var messageAppToTrack:String = "Please press button to determine your top app to track"
    //var isTracking:Boolean = false

}