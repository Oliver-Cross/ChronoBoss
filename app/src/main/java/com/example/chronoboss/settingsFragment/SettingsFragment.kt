package com.example.chronoboss.settingsFragment

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
import com.example.chronoboss.databinding.FragmentSettingsBinding
import com.example.chronoboss.statsFragment.StatsViewModel
import com.example.chronoboss.statsFragment.StatsViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSettingsBinding>(inflater,
            R.layout.fragment_settings,container,false)

        // Room database setup
        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getDatabase(application).dayDao()
        val viewModelFactory = SettingsViewModelFactory(dataSource, application)
        val settingsViewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding.settingsViewModel = settingsViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }

}