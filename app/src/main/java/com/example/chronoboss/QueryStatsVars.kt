package com.example.chronoboss

import android.app.usage.UsageStats
import android.content.Context


class QueryStatsVars {

    val queryStatsUtils:QueryStatsUtils = QueryStatsUtils()
    var tPck:UsageStats? = queryStatsUtils.getTopPackage()
    var tPckNme:String? = queryStatsUtils.getTopPackage()?.packageName
    var tPckTme:Long? = queryStatsUtils.getTopPackage()?.totalTimeInForeground


}