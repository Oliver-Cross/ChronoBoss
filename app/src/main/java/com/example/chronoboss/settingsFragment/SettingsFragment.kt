package com.example.chronoboss.settingsFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_settings.*

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
        val settingsViewModel: SettingsViewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        var switch = option_push_notifications_switch?.setOnCheckedChangeListener { _, isChecked ->
            saveData()
            Toast.makeText(context, "checkbox changed", Toast.LENGTH_SHORT).show()
        }

        var seekB = option_app_budget_slider?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                option_app_budget_slider.progress = progress
                Toast.makeText(context, "value is: " + progress.toString(), Toast.LENGTH_LONG).show()
                loadData()
                saveData()
                Toast.makeText(context, "has this saved?", Toast.LENGTH_SHORT).show()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                loadData()
            }
        })

        binding.settingsViewModel = settingsViewModel

       binding.setLifecycleOwner(this)

         return binding.root



    }

    private fun saveData(){
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPrefs?.edit()?:return

        with(sharedPrefs.edit()){
            putBoolean("NOTIFICATIONS_KEY", option_push_notifications_switch.isChecked)
            putInt("APP_BUDGET_KEY", option_app_budget_slider.progress)
        }
    }

    fun loadData(){
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val saved_notifications_switch = sharedPrefs?.getBoolean("NOTIFICATIONS_KEY",true)
        val saved_seek_progress = sharedPrefs?.getInt("APP_BUDGET_KEY",120)

        if (saved_notifications_switch != null) {
            option_push_notifications_switch.isChecked = saved_notifications_switch
        }
        if (saved_seek_progress != null) {
          option_app_budget_slider.progress = saved_seek_progress
       }
    }

}