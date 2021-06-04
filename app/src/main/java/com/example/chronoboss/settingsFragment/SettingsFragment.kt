package com.example.chronoboss.settingsFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.chronoboss.database.DayViewModel
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
    private lateinit var mDayViewModel: DayViewModel


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
            saveDataToggle()
            Toast.makeText(context, "checkbox changed", Toast.LENGTH_SHORT).show()
        }

        //loadData()





        var seekC = binding.optionAppBudgetSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                option_app_budget_slider.progress = progress
                Log.i("Settings fata()\n" +
                        "                saveDatragment C, on progress changed", progress.toString())
                //loadDa()
                saveDataSlider()
                binding.appBudgetSliderValue.text = progress.toString()
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //loadData()
            }
        })

        binding.settingsViewModel = settingsViewModel


        binding.setLifecycleOwner(this)

        //saveData()

        return binding.root



    }

    private fun saveDataToggle(){
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        Log.i("save Data toggle", "saved progress. 1" + binding.optionAppBudgetSlider.progress.toString())
        val editor : SharedPreferences.Editor = sharedPrefs?.edit()?:return
        Log.i("save Data toggle", "saved progress, 2" + binding.optionAppBudgetSlider.progress.toString())


        with(editor){
            //putBoolean("NOTIFICATIONS_KEY", option_push_notifications_switch.isChecked)
            putBoolean("NOTIFICATIONS_KEY", binding.optionPushNotificationsSwitch.isChecked)
            //putInt("APP_BUDGET_KEY", option_app_budget_slider.progress)
            Log.i("save Data toggle", "saved progress, 3" + binding.optionAppBudgetSlider.progress.toString())
        }



    }

    private fun saveDataSlider(){
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        Log.i("save Data slider", "saved progress. 1" + binding.optionAppBudgetSlider.progress.toString())
        val editor : SharedPreferences.Editor = sharedPrefs?.edit()?:return
        Log.i("save Data slider", "saved progress, 2" + binding.optionAppBudgetSlider.progress.toString())


        with(editor){
            //putBoolean("NOTIFICATIONS_KEY", option_push_notifications_switch.isChecked)
            putInt("APP_BUDGET_KEY", binding.optionAppBudgetSlider.progress)
            Log.i("save Data slider", "saved progress, 3" + binding.optionAppBudgetSlider.progress.toString())
        }

        mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)
        mDayViewModel.updateTimeLimit(binding.optionAppBudgetSlider.progress.toLong())


    }

    fun loadData(){
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val saved_notifications_switch = sharedPrefs?.getBoolean("NOTIFICATIONS_KEY",true)
        val saved_seek_progress = sharedPrefs?.getInt("APP_BUDGET_KEY", binding.optionAppBudgetSlider.progress)
        Log.i("load Data", "loaded progress" + saved_seek_progress.toString())

        //mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)
        //mDayViewModel.updateTimeLimit(binding.optionAppBudgetSlider.progress.toLong())

        if (saved_notifications_switch != null) {
            binding.optionPushNotificationsSwitch.isChecked = saved_notifications_switch
        }
        if (saved_seek_progress != null) {
            binding.optionAppBudgetSlider.progress = saved_seek_progress
        }
    }

}