package com.example.chronoboss

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

/** utility class providing functionality relating to queries to the OS
 *
 */
class QueryStatsUtils {

    lateinit var topPackage:UsageStats
    //val targetPackage: UsageStats? = null

    /** function to request access to usage statistics which is called upon setup
     *
     */
    fun requestUsageStatsPermission(activity: Activity) {
        val intent: Intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        activity.startActivity(intent)
    }

    /** function to check whether access to usage statistics has already been allowed
     *
     */
    fun hasStatsPermission(context: Context, activity: Activity): Boolean {

        val applicationInfo: ApplicationInfo? =
            context?.packageName.let { activity?.packageManager?.getApplicationInfo(it, 0) }
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

    /** function to get list of usage statistics
     * returns a list where each package has available information that can
     * be accessed such as package name
     */
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

    /** function for getting wasted time with the intention of being used
     * for the database to pull stats by day
     */
    fun getTimeWasted(context:Context, beginTime:Long, endTime:Long):Long{
        val targetPackageName:String = "com.android.chrome"
        var timeWasted:Long = 0
        val statsManager =
            context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val milliDay = 86400000
        val usageStats: List<UsageStats> =
            statsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime)
        for(pck in usageStats){
            if(pck.packageName == targetPackageName){
                timeWasted = pck.totalTimeInForeground
            }
        }
        return timeWasted
    }

    /*fun getTargetPackage(context: Context?): UsageStats? {

        val usageStats = getStats(context)
        for (pck in usageStats) {
            if (pck == targetPackage) {
                return pck
            }
        }
        return null
    } */

    /*fun getTargetTimeForeground(): Long? {
        return targetPackage?.totalTimeInForeground
    } */

    /*@RequiresApi(Build.VERSION_CODES.Q)
    fun getTargetTimeVisible(): Long? {
        return targetPackage?.totalTimeVisible
    } */

    /*fun getTargetName(): String? {
        return targetPackage?.packageName
    } */

    /** function that pulls usage statistics then returns the top package used
     *
     */
    fun getTopPackage(context: Context?): UsageStats {
        val usageStats = getStats(context)
        var timeUsed: Long = 0
        for (pck in usageStats) {
            if ((pck.totalTimeInForeground > timeUsed) &&
                (pck.packageName != "com.google.android.apps.nexuslauncher") &&
                (pck.packageName != "com.example.chronoboss")
            ) {
                timeUsed = pck.totalTimeInForeground
                topPackage = pck
            }
        }
        return topPackage
    }

    /** returns the time in foreground for the top package used
     *
     */
    fun getTopPackageForeground(context: Context?): Long {
        val topPck = getTopPackage(context)
        return topPck.totalTimeInForeground
    }

    /** returns the package name for the top package used
     *
     */
    fun getTopPackName(context: Context?): String {
        val topPck = getTopPackage(context)
        return topPck.packageName
    }

    /*@RequiresApi(Build.VERSION_CODES.Q)
    fun getPckTimeVisible(context: Context?): Long? {
        val topPck = getTopPackage(context)
        return topPck?.totalTimeVisible
    } */
}


