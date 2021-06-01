package com.example.chronoboss

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.*
import android.widget.TextView
import android.widget.TextView.*
import android.widget.Toast

import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_test.*

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
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    /** function to open settings to allow access to usage stats from OS */
    fun requestUsageStatsPermission() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    fun hasPermission():Boolean {
        val applicationInform: ApplicationInfo? =
            context?.packageName?.let { activity?.packageManager?.getApplicationInfo(it, 0) }
        val appOps: AppOpsManager =
            context?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode: Int? = applicationInform?.let {
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                it.uid,
                applicationInform.packageName
            )
        }
        return (mode == AppOpsManager.MODE_ALLOWED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_settings, container, false)

        val queryStatsUtils:QueryStatsUtils = QueryStatsUtils()
        if(!hasPermission()){
            requestUsageStatsPermission()
        }


        // Inflate the layout for this fragment

        val v:ImageView = view.findViewById(R.id.appIcon)
        var appToTrack:UsageStats? = queryStatsUtils.getTopPackage(context)

        val topPackageIcon: Drawable? = appToTrack?.packageName?.let {
            activity?.packageManager?.getApplicationIcon(
                it
            )
        }
        v.setImageDrawable(topPackageIcon)

          val seekBar:SeekBar

         seekBar = view.findViewById(R.id.option_app_budget_slider)

        seekBar.max = 50
        
         var textView:TextView
        textView = view.findViewById(R.id.app_budget_slider_value)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = ("min " + progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })




        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
