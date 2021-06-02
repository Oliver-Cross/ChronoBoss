package com.example.chronoboss


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService


class MyAlarm : BroadcastReceiver() {

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationID = 101

    override fun onReceive(context: Context?, intent: Intent?) {
        val testName: String = "com.android.settings"
        val lim: Long = 12217
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
                            //abortBroadcast
                            Toast.makeText(context, "limit has been reached", Toast.LENGTH_LONG)
                            .show()
                            sendNotification(context)
                            //abortBroadcast
                        }
                    }
                }
            }
        }
    }

    /*private fun showNotification(context: Context?) {
        val contentIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        val mBuilder: NotificationCompat.Builder? = context?.let {
            NotificationCompat.Builder(it).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("my notification").setContentText("hello world")
        }
        if (mBuilder != null) {
            mBuilder.setContentIntent(contentIntent)
            mBuilder.setDefaults(Notification.DEFAULT_SOUND)
            mBuilder.setAutoCancel(true)
        }
        val mNotificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (mBuilder != null) {
            mNotificationManager.notify(1, mBuilder.build())
        }
    }
} */

    /**private void showNotification(Context context) {
    mBuilder.setContentIntent(contentIntent);
    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
    mBuilder.setAutoCancel(true);
    NotificationManager mNotificationManager =
    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.notify(1, mBuilder.build());

    } */


    private fun createNotificationChannel(context: Context?) {
        // Creates notification on versions Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register channel with system
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(context: Context?) {
        val intent: Intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // Bitmap converter
        //val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.time)
        //val bitmapLargeIcon = BitmapFactory.decodeResource(context?.resources, R.drawable.clock)


        val builder = context?.let {
            NotificationCompat.Builder(it, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Facebook time-limit reached")
                .setContentText("You have used Facebook for 50/50 minutes today.")
                // Set icons
                //.setLargeIcon(bitmap)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Yo man, you really need to get off Facebook. Why don't you go for a walk or something?")
                )
                // Set intent so you can click on it
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        }

        if (builder != null) {
            context?.let { NotificationManagerCompat.from(it) }?.let {
                with(it) {
                    this?.notify(notificationID, builder.build())
                }
            }
        }
    }
}






