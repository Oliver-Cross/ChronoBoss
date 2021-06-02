package com.example.chronoboss


import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.*


class MyAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var topPackageName: String? = null
        //Toast.makeText(context, "connected now", Toast.LENGTH_SHORT).show()
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
        if (stats != null) {
            var runningTask: SortedMap<Long, UsageStats> = TreeMap<Long, UsageStats>()
            for (usageStats in stats) {
                runningTask.put(usageStats.lastTimeUsed, usageStats)
            }
            if (runningTask.isEmpty()) {
                topPackageName = "none"
            } else {
                topPackageName = runningTask.get(runningTask.lastKey())?.packageName

            }
            Toast.makeText(context, topPackageName, Toast.LENGTH_SHORT).show()
        }
    }
}




