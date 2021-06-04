package com.example.chronoboss

import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/** service class that runs upon app launch to track app usage in real time, with the purpose
 * of sending notifications once the time limit has been reached
 */
class MakeItWork : Service() {

    private var mHandler: Handler? = null
    var isMidnight:Boolean = false
    var setLimit:Long = 50000
    lateinit var context:Context

    var limReached:Boolean = false

    /** onCreate called when service is created */
    override fun onCreate() {
        super.onCreate()
        this.context = this
    }

    /** task to run once query has been started in MainActivity upon launch
     * queries usage data on given interval and checks against limit
     * if limit has been reached, a message is displayed and the service delays queries
     */
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            //replace this with target app from shared preferences
            var name: String = "com.android.settings"
            //replace this with limit from settings
            var limit: Long = 100000

            val usage: UsageStatsManager =
                context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            var time: Long? = System.currentTimeMillis()
            var stats: List<UsageStats>? = time?.let {
                usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, it)
            }
            limReached = false
            val z:ZoneId = ZoneId.of( "Pacific/Auckland")
            var now:ZonedDateTime = ZonedDateTime.now(z)
            var tomorrow :LocalDate = now.toLocalDate().plusDays(1);
            val tomorrowStart: ZonedDateTime = tomorrow.atStartOfDay(z)
            val duration: Duration = Duration.between(now, tomorrowStart)
            val millisecondsUntilTomorrow: Long = duration.toMillis()


            if (stats != null) {
                for (usageStats in stats) {
                    if (usageStats.packageName == name) {
                        var currentTimeMill: Long? = usageStats.totalTimeInForeground
                        if (currentTimeMill != null) {
                            if(millisecondsUntilTomorrow <= 0){
                                isMidnight = true
                                setLimit = usageStats.totalTimeInForeground + limit
                                DEFAULT_QUERY_INTERVAL = (60*1000).toLong()

                            }
                            //add check to shared preferences whether notifications are enabled
                            if (currentTimeMill >= limit) {
                                Toast.makeText(context, "You have reached your limit of " + ((limit)/(60000)).toString() + " minutes!", Toast.LENGTH_LONG).show()
                                limReached = true
                                DEFAULT_QUERY_DONE = (millisecondsUntilTomorrow)
                            }
                            //this code is just for testing - can remove later
                            /*else {
                                    Toast.makeText(context, "limit not reached" + currentTimeMill.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                } */
                        }
                    }
                }
                if (limReached) {
                    mHandler!!.postDelayed(this, DEFAULT_QUERY_DONE)
                } else {
                    mHandler!!.postDelayed(this, DEFAULT_QUERY_INTERVAL)
                }
            }
        }
    }

    /** called when service is started */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //limTest = intent.getIntExtra("varTest", 0)

        //Toast.makeText(context, "this is the number: " + limTest, Toast.LENGTH_LONG).show()

        //create handler instance
        mHandler = Handler()
        //Execute a runnable task as soon as possible
        mHandler!!.post(runnableService)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        //interval for querying data if limit has not been reached
        var DEFAULT_QUERY_INTERVAL = (60 * 1000).toLong()
        //interval for querying data if limit has not been reached
        var DEFAULT_QUERY_DONE = (43200000).toLong()
    }
}