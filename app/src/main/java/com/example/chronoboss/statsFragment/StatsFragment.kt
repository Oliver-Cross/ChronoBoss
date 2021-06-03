package com.example.chronoboss.statsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.R
import com.example.chronoboss.database.DayDatabase
import com.example.chronoboss.databinding.FragmentProgressBinding
import com.example.chronoboss.databinding.FragmentStatsBinding
import com.example.chronoboss.progressFragment.ProgressViewModel
import com.example.chronoboss.progressFragment.ProgressViewModelFactory
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentStatsBinding>(inflater,
            R.layout.fragment_stats,container,false)

        // Room database setup
        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getDatabase(application).dayDao()
        val viewModelFactory = StatsViewModelFactory(dataSource, application)
        val statsViewModel = ViewModelProvider(this, viewModelFactory).get(StatsViewModel::class.java)

        binding.statsViewModel = statsViewModel

        binding.setLifecycleOwner(this)

        val chart = binding.statsGraph

        setLineChartData(chart)

        return binding.root
    }
    @SuppressLint("ResourceAsColor")
    fun setLineChartData(line_chart: LineChart){

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
        val lineEntry: ArrayList<Entry> = ArrayList()
        lineEntry.add(Entry(0f, 90F))
        lineEntry.add(Entry(1f, 95F))
        lineEntry.add(Entry(2f, 75F))
        lineEntry.add(Entry(3f, 80F))
        lineEntry.add(Entry(4f, 85F))
        lineEntry.add(Entry(5f, 90F))
        lineEntry.add(Entry(6f, 95F))

        val progressDataSet = LineDataSet(lineEntry, "Progress of the Week!")
        progressDataSet.color = resources.getColor(R.color.teal_200)

        val data = LineData(progressDataSet)

        line_chart.data = data
        line_chart.setBackgroundColor(R.color.white)
        line_chart.animateXY(2000, 2000)


    }
}