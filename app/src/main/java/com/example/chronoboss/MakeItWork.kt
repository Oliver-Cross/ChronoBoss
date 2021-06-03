package com.example.chronoboss

import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

class MakeItWork : Service() {
    private var mHandler: Handler? = null
    lateinit var context:Context
    var limReached:Boolean = false

    override fun onCreate() {
        super.onCreate()
        this.context = this
    }

    // task to be run here
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            val name: String = "com.android.settings"
            val limit: Long = 79000
            val usage: UsageStatsManager =
                context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            var time: Long? = System.currentTimeMillis()
            var stats: List<UsageStats>? = time?.let {
                usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, it)
            }
            limReached = false
            if (stats != null) {
                for (usageStats in stats) {
                    if (usageStats.packageName == name) {
                        var currentTimeMill: Long? = usageStats.totalTimeInForeground
                        if (currentTimeMill != null) {
                            if (currentTimeMill >= limit) {
                                Toast.makeText(context, "limit reached", Toast.LENGTH_SHORT).show()
                                limReached = true
                                val intent:Intent = Intent(context, MakeItWork::class.java)
                                stopService(intent)
                            } else {
                                Toast.makeText(context, "limit not reached", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
                mHandler!!.postDelayed(this, DEFAULT_SYNC_INTERVAL)
            }
        }
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Create the Handler object
        mHandler = Handler()
        // Execute a runnable task as soon as possible
        mHandler!!.post(runnableService)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        // default interval for syncing data
        const val DEFAULT_SYNC_INTERVAL = (5 * 1000).toLong()
    }
}