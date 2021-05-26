package com.example.chronoboss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.provider.Settings
import com.google.android.material.internal.ContextUtils
import com.google.android.material.internal.ContextUtils.getActivity
import java.security.AccessController.getContext
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QueryStatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QueryStatsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    /*private var param1: String? = null
    private var param2: String? = null
     */

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    } */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_query_stats, container, false)
    }

    fun requestUsageStatsPermission() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }


    fun getStats(context: Context): List<UsageStats> {
        requestUsageStatsPermission()
        val statsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val milliDay = 86400000
        val calendar: Calendar = Calendar.getInstance()
        val endTime: Long = calendar.timeInMillis
        val beginTime: Long = endTime - milliDay
        val usageSt: List<UsageStats> =
            statsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime)
        return usageSt
    }

    fun getTopPackage(packageList:List<UsageStats>): String {
        var timeUsed: Long = 0
        var topPack: UsageStats? = null
        for (pck in packageList) {
            if (pck.totalTimeInForeground > timeUsed) {
                timeUsed = pck.totalTimeInForeground
                topPack = pck
            }
        }
        if (topPack != null) {
            return topPack.packageName
        }
        return "default package"

    }

    fun setTopPackage(context:Context){
        val ls = getStats(context)
        //dataField = getTopPackage()

}


//UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService("usagestats");






/*companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueryStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
        QueryStatsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
} */
}