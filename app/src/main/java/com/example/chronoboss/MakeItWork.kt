package com.example.chronoboss

import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/** service class that runs upon app launch to track app usage in real time, with the purpose
 * of sending notifications once the time limit has been reached
 */
class MakeItWork : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var mHandler: Handler? = null
    var isMidnight:Boolean = false
    var setLimit:Long = 500000
    lateinit var context:Context
    lateinit var appName:String
    var timeLimit:Long = 500000

    var limReached:Boolean = false

    /** onCreate called when service is created */
    override fun onCreate() {
        super.onCreate()
        this.context = this
    }

    override fun onDestroy(){
        super.onDestroy()
        job.cancel()
    }

    /** task to run once query has been started in MainActivity upon launch
     * queries usage data on given interval and checks against limit
     * if limit has been reached, a message is displayed and the service delays queries
     */
    private val runnableService: Runnable = object : Runnable {
        override fun run() {

            val usage: UsageStatsManager =
                context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            var time: Long = System.currentTimeMillis()
            var stats: List<UsageStats> = time.let {
                usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, it)
            }
            limReached = false
            val z:ZoneId = ZoneId.of( "Pacific/Auckland")
            var now:ZonedDateTime = ZonedDateTime.now(z)
            var tomorrow :LocalDate = now.toLocalDate().plusDays(1);
            var tomorrowStart: ZonedDateTime = tomorrow.atStartOfDay(z)
            var duration: Duration = Duration.between(now, tomorrowStart)
            var millisecondsUntilTomorrow: Long = duration.toMillis()


            if (stats != null) {
                for (usageStats in stats) {
                    if (usageStats.packageName == appName) {
                        var currentTimeMill: Long = usageStats.totalTimeInForeground
                        if (currentTimeMill != null) {
                            Toast.makeText(context, "not null", Toast.LENGTH_LONG)
                            if(millisecondsUntilTomorrow <= 0){
                                isMidnight = true
                                //setLimit = usageStats.totalTimeInForeground + timeLimit
                                DEFAULT_QUERY_INTERVAL = (millisecondsUntilTomorrow).toLong()
                            }
                            if (currentTimeMill >= timeLimit*60000) {
                                Toast.makeText(context, "You have reached your limit of " + (timeLimit).toString() + " minutes on " + appName.substringAfterLast('.') + "!", Toast.LENGTH_LONG).show()
                                limReached = true
                                DEFAULT_QUERY_DONE = (10*1000)
                            }
                            //this code is just for testing - can remove later
                            else {
                                    Toast.makeText(context, "Limit not reached of: " + timeLimit.toString() + " minutes, time used: " + (currentTimeMill/60000).toString() + " minutes", Toast.LENGTH_SHORT)
                                        .show()
                                }
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
        scope.launch {
            val db = Room.databaseBuilder(
                context.applicationContext,
                DayDatabase::class.java,
                "day_database"
            ).build()
            appName = (db.dayDao().getTodayDay().application)
            timeLimit = (db.dayDao().getTodayDay().timeLimit)
            //setLimit = ((db.dayDao().getTodayDay().timeWasted*60000 + (timeLimit*60000)))
        }

        //Toast.makeText(context, appName, Toast.LENGTH_LONG).show()

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
        var DEFAULT_QUERY_INTERVAL = (10 * 1000).toLong()
        //interval for querying data if limit has not been reached
        var DEFAULT_QUERY_DONE = (10 * 1000).toLong()
    }
}