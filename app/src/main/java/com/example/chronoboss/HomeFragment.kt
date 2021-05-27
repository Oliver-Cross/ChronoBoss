package com.example.chronoboss

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.media.Image
import android.provider.Settings
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    /*private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    } */

    /**override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_home)
        setContentView(R.layout.fragment_home
        setIcon()
    }*/

    override fun onCreateView(

        /*inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        val icon:ImageView = view.findViewById(R.id.app_icon)
        val chrome:Drawable? = activity?.packageManager?.getApplicationIcon("com.android.chrome")
        icon.setImageDrawable(chrome)
        return view */
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate view
        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        val icon:ImageView = view.findViewById(R.id.app_icon)
        val chrome:Drawable? = activity?.packageManager?.getApplicationIcon("com.android.chrome")
        icon.setImageDrawable(chrome)
        //check if has stats access, if not request
        if(!hasPermission()){
            requestUsageStatsPermission()
        }
        //find the top package
        val top:UsageStats? = getTopPackage()
        //find the textview for the top package name
        val setTopView: TextView? = view.findViewById(R.id.top_package_name)
        //set the text to the top package name
        setTopView?.setText(top?.packageName)
        //convert the time the top app was used to a string
        val topTimeString:String? = top?.totalTimeInForeground.toString()
        //find the textview for the top package time used
        val timeV: TextView? = view.findViewById(R.id.top_package_time)
        //set it to the time the app was used
        timeV?.setText(topTimeString)
        //return the view that was inflated
        return view

    }

    fun requestUsageStatsPermission() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    /** function to query the usage statistics from the OS in given interval */
    fun getStats(context: Context?): List<UsageStats> {
        val statsManager =
            context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val milliDay = 86400000
        val endTime: Long = System.currentTimeMillis()
        val beginTime: Long = endTime - milliDay
        val usageSt: List<UsageStats> =
            statsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime)
        return usageSt
    }

    /** function to get the top package name based on the list of packages gathered
     * from the query
     */
    fun getTopPackage(): UsageStats?{
        val usageSt = getStats(context)
        var timeUsed:Long = 0
        var topPack: UsageStats? = null
        for(pck in usageSt){
            if((pck.totalTimeInForeground > timeUsed ) &&
                (pck.packageName != "com.google.android.apps.nexuslauncher") &&
                (pck.packageName != "com.example.chronoboss")){
                timeUsed = pck.totalTimeInForeground
                topPack = pck
            }

        }
        return topPack
    }

    /** function to check whether our app has been granted access to query usage
     * statistics from the operating system
     */
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


    /*fun getIcon(): Drawable? {
        return activity?.packageManager?.getApplicationIcon("com.android.chrome")
    } */

    /*fun setIcon(){
        val icon = activity?.packageManager?.getApplicationIcon("com.android.chrome")
        val view: ImageView? = activity?.findViewById(R.id.app_icon)
        if(view != null){
            view.setImageDrawable(icon)
        }else print("view is null") */
        //image.setImageDrawable(getIcon())
        /*val view: ImageView? = activity?.findViewById(R.id.app_icon)
        if (view != null) {
            view.setImageDrawable(getIcon())

        } */
        //return image
    }

    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    } */
