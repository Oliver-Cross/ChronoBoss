package com.example.chronoboss.statsFragment

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


        return binding.root
    }
    
}