package com.example.chronoboss

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit
import android.net.ConnectivityManager
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    val myAlarm: MyAlarm = MyAlarm()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize navigation bar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_controller) as NavHostFragment
        val navController = navHostFragment.navController
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        navigationView.setupWithNavController(navController)
    }

    override protected fun onStart() {
        super.onStart()
        setAlarm()
        val intentFilter: IntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myAlarm, intentFilter)
    }

    override protected fun onStop() {
        super.onStop()
        //unregisterReceiver(myAlarm)
    }

    /*fun goQueryStats(view:View) {
        val intent = Intent(this, QueryStatsActivity::class.java)
        startActivity(intent)
    } */

    fun setAlarm() {
        val timeInMillis: Long = TimeUnit.MINUTES.toMillis(1)
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent: Intent = Intent(this, MyAlarm::class.java)
        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime(),
            timeInMillis,
            pendingIntent
        )
    }
}