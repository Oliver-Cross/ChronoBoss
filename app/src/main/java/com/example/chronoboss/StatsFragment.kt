package com.example.chronoboss

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.chronoboss.databinding.FragmentProgressBinding
import com.example.chronoboss.databinding.FragmentStatsBinding
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val line_chart : LineChart = LineChart(context)
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

        binding = DataBindingUtil.inflate<FragmentStatsBinding>(inflater,
            R.layout.fragment_stats,container,false)
        return binding.root
    }
/*
    @SuppressLint("ResourceAsColor")
    fun setLineChartData(){

        val line_chart : LineChart = LineChart(context)
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
    */
}