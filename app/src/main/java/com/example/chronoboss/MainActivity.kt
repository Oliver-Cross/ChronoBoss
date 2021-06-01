package com.example.chronoboss

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    /*companion object {
        private const val READ_PHONE_STATE_PERMISSION_CODE = 100
    } */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*checkPermission(
            Manifest.permission.READ_PHONE_STATE,
            READ_PHONE_STATE_PERMISSION_CODE) */

        val pollingUtils:PollingUtils = PollingUtils()
        val pollingService:PollingService = PollingService()
        pollingUtils.startPollingService(this, 5, PollingService::class.java, pollingService.ACTION)

        //Initialize navigation bar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_controller) as NavHostFragment
        val navController = navHostFragment.navController
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        navigationView.setupWithNavController(navController)
    }

    fun goQueryStats(view:View) {
        val intent = Intent(this, QueryStatsActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Stop polling service
        println("Stop polling service...")
        val pollingUtils:PollingUtils = PollingUtils()
        val pollingService:PollingService = PollingService()
        pollingUtils.stopPollingService(this, PollingService::class.java, pollingService.ACTION)
    }

    /*fun setAlarm(){
        val timeInMillis:Long = TimeUnit.MINUTES.toMillis(1)
        val alarmManager:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent:Intent = Intent(this, MyAlarm::class.java)
        var pendingIntent:PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), timeInMillis, pendingIntent)
    } */

    /*private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    } */
}