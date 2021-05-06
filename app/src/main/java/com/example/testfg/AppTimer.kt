package com.example.testfg

import androidx.appcompat.app.AppCompatActivity
import com.example.testfg.R
import android.content.Intent
import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.Context
import android.os.*
import android.provider.Settings
import android.util.Log
import com.rvalerio.fgchecker.AppChecker

@Suppress("DEPRECATION")
class AppTimer() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    var count = 12000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_timer)
        //decrementTimer()

    }

    fun requestUsageStatsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                !hasUsageStatsPermission(this)) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun hasUsageStatsPermission(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow("android:get_usage_stats",
                Process.myUid(), context.packageName)
        return mode == AppOpsManager.MODE_ALLOWED
    }

    fun namePackage() {
        val appChecker = AppChecker()
        val nameOfPackage: String = appChecker.getForegroundApp(this.applicationContext)
    }


    /** checks for the foreground app - whether it is the target app - and decrements count periodically
     *
     */
    fun checkForeground():Int {
        val context = this.applicationContext
        if (!hasUsageStatsPermission(context)){
          requestUsageStatsPermission()
        }
        val appChecker = AppChecker()
        appChecker.`when`("com.example.myapplication", AppChecker.Listener() {
            @Override
            fun onForeground(packageName: String) {
              count--
            }
            appChecker.timeout(6000)
            appChecker.start(this)
        }
        )
        return count
    }

    fun decrementTimer(){
        while(count > 0){
            checkForeground()
        }
        Log.d("hello", "world")

    }

    /** placeholder for what to do while there is still time remaining on the timer
     *
     */
    /**fun countdown(){
        while(checkForeground() > 0){
            //do stuff
        }
    } */


    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppTimer> {
        override fun createFromParcel(parcel: Parcel): AppTimer {
            return AppTimer(parcel)
        }

        override fun newArray(size: Int): Array<AppTimer?> {
            return arrayOfNulls(size)
        }
    }
}




