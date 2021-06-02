package com.example.chronoboss


import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class MyAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val testName:String = "com.android.settings"
        val lim:Long = 12217
        val usage: UsageStatsManager = context?.getSystemService(
            Context.USAGE_STATS_SERVICE
        ) as UsageStatsManager
        var time: Long? = System.currentTimeMillis()
        var stats: List<UsageStats>? =
            time?.let {
                usage.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000,
                    it
                )
            }
        var limReached:Boolean? = false
        if (stats != null) {
            for (usageStats in stats) {
                if(usageStats.packageName == testName){
                    var currentTmeMill:Long? = usageStats.totalTimeInForeground
                    if(currentTmeMill != null){
                        if(currentTmeMill >= lim){
                            abortBroadcast
                            Toast.makeText(context, "limit has been reached",  Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}




