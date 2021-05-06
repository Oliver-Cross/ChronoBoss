package com.example.testingapplication

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    // Create channel and notification IDs
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationID = 101
    lateinit var usageStats: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usageStats = findViewById(R.id.usageStats)
        if (checkUsageStatsPermission()){
            showUsageStats()
        } else {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        // Creates channel if not currently active
        createNotificationChannel()

        val notificationButton: Button = findViewById(R.id.notification_button)
        notificationButton.setOnClickListener{
            sendNotification()
            if (checkUsageStatsPermission()){
                showUsageStats()
            } else {
                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
            }
        }

    }

    private fun showUsageStats() {
        var usageStatsManager: UsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        var cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        var queryUsageStats: List<UsageStats> = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.timeInMillis, System.currentTimeMillis())
        var stats_data: String = ""
        for (i in 0..queryUsageStats.size-1) {
            stats_data = stats_data + "Package Name: " + queryUsageStats.get(i).packageName + "\n" +
                    "Last Time Used : " + convertTime(queryUsageStats.get(i).lastTimeUsed) + "\n" +
                    "Describe Contents : " + queryUsageStats.get(i).describeContents() + "\n" +
                    "First Time Stamp : " + convertTime(queryUsageStats.get(i).firstTimeStamp) + "\n" +
                    "Last Time Stamp : " + convertTime(queryUsageStats.get(i).lastTimeStamp) + "\n" +
                    "Total Time in Foreground : " + convertTime2(queryUsageStats.get(i).totalTimeInForeground) + "\n\n";
        }
        usageStats.setText(stats_data)
    }

    private fun convertTime(lastTimeUsed: Long): String {
        var date: Date = Date(lastTimeUsed)
        var format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return format.format(date)
    }

    private fun convertTime2(lastTimeUsed: Long): String {
        var date: Date = Date(lastTimeUsed)
        var format: SimpleDateFormat = SimpleDateFormat("hh:mm", Locale.ENGLISH)
        return format.format(date)
    }


    private fun checkUsageStatsPermission(): Boolean {
        var appOpsManager: AppOpsManager ? = null
        var mode: Int = 0
        appOpsManager = getSystemService((APP_OPS_SERVICE))!! as AppOpsManager
        mode = appOpsManager.checkOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        return mode == MODE_ALLOWED
    }

    fun showtime2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val start = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val end = ZonedDateTime.now().toInstant().toEpochMilli()

            val usageStatsManager =
                getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val stats = usageStatsManager.queryAndAggregateUsageStats(start, end)

            val total = Duration.ofMillis(stats.values.map { it.totalTimeInForeground }.sum())
            println("YOU SPENT ${total.toMinutes()} mins.")
        }
    }


    private fun createNotificationChannel() {
        // Creates notification on versions Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register channel with system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // Bitmap converter
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.time)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.clock)



        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Facebook time-limit reached")
            .setContentText("You have used Facebook for 50/50 minutes today.")
                // Set icons
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Yo man, you really need to get off Facebook. Why don't you go for a walk or something?"))
                // Set intent so you can click on it
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationID, builder.build())
        }
    }



}