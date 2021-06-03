package com.example.chronoboss

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class PollingUtils {

    fun startPollingService(context:Context, seconds:Long, cls:Class<*>, action:String) {
        val am: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent: Intent = Intent(context, cls)
        intent.setAction(action)
        val pendingIntent: PendingIntent =
            PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        var triggerAtTime: Long = SystemClock.elapsedRealtime()
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, seconds * 1000, pendingIntent)
    }

    fun stopPollingService(context: Context?, cls:Class<*>, action: String){
        val am:AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent:Intent = Intent(context, cls)
        intent.setAction(action)
        val pendingIntent:PendingIntent = PendingIntent.getService(context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT)
        am.cancel(pendingIntent)
    }
}





