package com.example.chronoboss.statsFragment

import android.annotation.SuppressLint
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.chronoboss.QueryStatsUtils
import com.example.chronoboss.R
import com.example.chronoboss.database.DayDatabase
import com.example.chronoboss.database.DayViewModel
import com.example.chronoboss.databinding.FragmentProgressBinding
import com.example.chronoboss.databinding.FragmentStatsBinding
import com.example.chronoboss.progressFragment.ProgressViewModel
import com.example.chronoboss.progressFragment.ProgressViewModelFactory
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentStatsBinding>(
            inflater,
            R.layout.fragment_stats, container, false
        )

        // Room database setup
        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getDatabase(application).dayDao()
        val viewModelFactory = StatsViewModelFactory(dataSource, application)
        val statsViewModel =
            ViewModelProvider(this, viewModelFactory).get(StatsViewModel::class.java)

        binding.statsViewModel = statsViewModel

        binding.setLifecycleOwner(this)

        val chart = binding.statsGraph


        setLineChartData(chart, statsViewModel)



        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    fun setLineChartData(line_chart: LineChart, statsViewModel: StatsViewModel) {

        val xvalues = ArrayList<String>()
        xvalues.add("Monday")
        xvalues.add("Tuesday")
        xvalues.add("Wednesday")
        xvalues.add("Thursday")
        xvalues.add("Friday")
        xvalues.add("Saturday")
        xvalues.add("Sunday")

        /* Line entry: requires an object Entry, has constructors:
                x: float, y: float, and option for a drawable to make it pretty later
         */

        val queryStatsUtils: QueryStatsUtils = QueryStatsUtils()
        val topPck: UsageStats? = queryStatsUtils.getTopPackage(context)
        val topPckNme:String? = topPck?.packageName

        val currentTime = System.currentTimeMillis()
        val dayMilli = 86400000
        val todayStart = currentTime - dayMilli
        val oneDayAgoStart = todayStart - dayMilli
        val twoDaysAgoStart = oneDayAgoStart - dayMilli
        val threeDaysAgoStart = twoDaysAgoStart - dayMilli
        val fourDaysAgoStart = threeDaysAgoStart - dayMilli
        val fiveDaysAgoStart = fourDaysAgoStart - dayMilli
        val sixDaysAgoStart = fiveDaysAgoStart - dayMilli

        val todayWasted: Float? = topPckNme?.let {
            getTimeWasted(context, todayStart, currentTime, it)?.toFloat()?.div(60000)
        }
        val oneDayAgoWasted: Float? = topPckNme?.let {
            getTimeWasted(context, oneDayAgoStart, todayStart,
                it
            )?.toFloat()?.div(60000)
        }
        val twoDaysAgoWasted: Float? = topPckNme?.let {
            getTimeWasted(context, twoDaysAgoStart, oneDayAgoStart,
                it
            )?.toFloat()?.div(60000)
        }
        val threeDaysAgoWasted: Float? = topPckNme?.let {
            getTimeWasted(context, threeDaysAgoStart, twoDaysAgoStart,
                it
            )?.toFloat()?.div(60000)
        }
        val fourDaysAgoWasted: Float? = topPckNme?.let {
            getTimeWasted(context, fourDaysAgoStart, threeDaysAgoStart,
                it
            )?.toFloat()?.div(60000)
        }
        val fiveDaysAgoWasted: Float? = topPckNme?.let {
            getTimeWasted(context, fiveDaysAgoStart, fourDaysAgoStart,
                it
            )?.toFloat()?.div(60000)
        }
        val sixDaysAgoWasted: Float? = topPckNme?.let {
            getTimeWasted(context, sixDaysAgoStart, fiveDaysAgoStart,
                it
            )?.toFloat()?.div(60000)
        }





        val lineEntry: ArrayList<Entry> = ArrayList()




        sixDaysAgoWasted?.let { Entry(0f, it) }?.let { lineEntry.add(it) }
        fiveDaysAgoWasted?.let { Entry(1f, it) }?.let { lineEntry.add(it) }
        fourDaysAgoWasted?.let { Entry(2f, it) }?.let { lineEntry.add(it) }
        threeDaysAgoWasted?.let { Entry(3f, it) }?.let { lineEntry.add(it) }
        twoDaysAgoWasted?.let { Entry(4f, it) }?.let { lineEntry.add(it) }
        oneDayAgoWasted?.let { Entry(5f, it) }?.let { lineEntry.add(it) }
        todayWasted?.let { Entry(6f, it) }?.let { lineEntry.add(it) }




        val progressDataSet = LineDataSet(lineEntry, "Minutes Remaining")
        progressDataSet.color = resources.getColor(R.color.teal_200)

        val data = LineData(progressDataSet)

        line_chart.data = data
        line_chart.setBackgroundColor(R.color.white)
        line_chart.animateXY(2000, 2000)




    }


    fun getTimeWasted(context: Context?, beginTime:Long, endTime:Long, targetPackageName: String):Long?{
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


}

