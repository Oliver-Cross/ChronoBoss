package com.example.chronoboss

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.preference.PreferenceManager
import android.provider.Settings


    class QueryStatsUtils (testInt:Int?){



        fun requestUsageStatsPermission(activity: Activity) {
            val intent: Intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            activity.startActivity(intent)
        }
        var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        var testVar:Int? = prefs.getInt("APP_BUDGET_KEY", 120)

        fun hasStatsPermission(context: Context, activity: Activity): Boolean {

            val applicationInfo: ApplicationInfo? =
                context?.packageName?.let { activity?.packageManager?.getApplicationInfo(it, 0) }
            val appOps: AppOpsManager =
                context?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode: Int? = applicationInfo?.let {
                appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    it.uid,
                    applicationInfo.packageName
                )
            }
            return (mode == AppOpsManager.MODE_ALLOWED)
        }

        fun getStats(context: Context?): List<UsageStats> {
            val statsManager =
                context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val milliDay = 86400000
            val endTime: Long = System.currentTimeMillis()
            val beginTime: Long = endTime - milliDay
            val usageStats: List<UsageStats> =
                statsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime)
            return usageStats
        }

        fun getTopPackage(context: Context?): UsageStats?{
            val usageStats = getStats(context)
            var timeUsed:Long = 0
            var topPackage: UsageStats? = null
            for(pck in usageStats){
                if((pck.totalTimeInForeground > timeUsed) &&
                    (pck.packageName != "com.google.android.apps.nexuslauncher") &&
                    (pck.packageName != "com.example.chronoboss")){
                    timeUsed = pck.totalTimeInForeground
                    topPackage = pck
                }
            }
            return topPackage
        }
    }


