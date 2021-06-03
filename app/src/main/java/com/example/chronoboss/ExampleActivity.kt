package com.example.chronoboss

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import java.util.concurrent.TimeUnit

class ExampleActivity : AppCompatActivity() {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        this.context = this
        val alarm: Intent = Intent(this.context, TestAlarm::class.java)
        var alarmRunning:Boolean? = (PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null)
        if(alarmRunning == false){
            val timeInMillis: Long = TimeUnit.MINUTES.toMillis(1)
            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent: Intent = Intent(this, TestAlarm::class.java)
            var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                timeInMillis,
                pendingIntent
            )
        }
    }
}