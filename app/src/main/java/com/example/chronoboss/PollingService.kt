package com.example.chronoboss

import android.annotation.SuppressLint
import android.app.*
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.os.IBinder
import android.widget.Toast

class PollingService : Service(){
    val ACTION:String = "com.example.service.PollingService"
    private lateinit var mNotification: Notification
    private lateinit var mManager:NotificationManager

    override fun onBind(intent:Intent):IBinder?{
        return null
    }

    override fun onCreate() {
        initNotifiManager()
    }
    override fun onStart(intent: Intent, startId:Int){
        PollingThread().start()
    }

    private fun initNotifiManager(){
        mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val icon:Int = R.drawable.ic_launcher_foreground
        mNotification = Notification()
        mNotification.icon = icon
        mNotification.tickerText = "new message"
        mNotification.defaults = Notification.DEFAULT_SOUND
        mNotification.flags = Notification.FLAG_AUTO_CANCEL
    }

    @SuppressLint("WrongConstant")

    /*private fun showNotification(){
        /*val intent:Intent = Intent(this, MainActivity::class.java)
        var pendingIntent:PendingIntent = PendingIntent.getActivity(this, 0, intent,
        FLAG_ACTIVITY_NEW_TASK) */
    } */



    class PollingThread : Thread() {
        override fun run(){
            //polling code here
            //do something when condition met

            val lim:Long = 1000
            val usage:UsageStatsManager =
        }
    }

    /**val testName: String = "com.android.settings"
    val lim: Long = 1000
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
    var limReached: Boolean? = false
    if (stats != null) {
    for (usageStats in stats) {
    if (usageStats.packageName == testName) {
    var currentTmeMill: Long? = usageStats.totalTimeInForeground
    if (currentTmeMill != null) {
    if (currentTmeMill >= lim) {
    //Toast.makeText(context, "limit reached: " + currentTmeMill.toString(), Toast.LENGTH_SHORT).show()
    //sendNotifications(context)
    createNotificationChannel(context)
    sendNotifications(context)

    val aManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    val intent:Intent = Intent(context, MyAlarm::class.java)
    val pendingIntent:PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    aManager.cancel(pendingIntent)

    //abortBroadcast
    //Toast.makeText(context, "limit has been reached: " + currentTmeMill.toString(), Toast.LENGTH_SHORT)
    //.show()

    //sendNotification(context)
    //abortBroadcast
    }else{
    Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show()
    }
    }
    }
    }
    }
    } */
}


    /**
     * Polling thread
     * Simulate asynchronous threads polling Server
     * @Author Ryan
     * @Create 2013-7-13 10:18:34 am
     */
    /**int count = 0;
    class PollingThread extends Thread {
        @Override
        publicvoid run() {
            System.out.println("Polling...");
            count ++;
            //Pop up notification when the count is divisible by 5
            if (count % 5 == 0) {
                showNotification();
                System.out.println("New message!");
            }
        }
    }
    @Override
    publicvoid onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }
}  */