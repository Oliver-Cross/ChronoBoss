package com.example.chronoboss.homeFragment

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.chronoboss.QueryStatsUtils
import com.example.chronoboss.R
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayDatabase
import com.example.chronoboss.database.DayViewModel
import com.example.chronoboss.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var mDayViewModel: DayViewModel
    private lateinit var binding: FragmentHomeBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Binding setup
        if(!hasPermission()){
            requestUsageStatsPermission()
        }
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)

        val icon: ImageView = binding.appIcon


        //val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        //val icon: ImageView = view.findViewById(R.id.app_icon)
        val queryStatsUtils: QueryStatsUtils = QueryStatsUtils()
        val topPck:UsageStats? = queryStatsUtils.getTopPackage(context)
        val topPckNme:String? = topPck?.packageName
        val topPackageIcon: Drawable? = topPckNme?.let {
            activity?.packageManager?.getApplicationIcon(
                it
            )
        }
        icon.setImageDrawable(topPackageIcon)


        Log.i("HomeFragment", "onCreateView Called")


        // Room database setup
        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getDatabase(application).dayDao()
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)




        //mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)
        //val readAllData = readAllData()

        binding.homeViewModel = homeViewModel

        //Insertion example
        val day = Day(1, 0, 120, "Top Application")
        homeViewModel.addDay(day)
        var tWaste:Long? = topPck?.totalTimeInForeground
        var converted:Long = 0
        if(tWaste != null){
            converted = (tWaste.toFloat()/60000.toFloat()).toLong()
        }

        if (converted != null && converted > 0) {
            homeViewModel.updateTodayTimeWasted(converted)
        }

        //val packageName = topPckNme?.substringAfterLast('.')
        val packageName = topPckNme
        packageName?.let { homeViewModel.setPackageName(it) }

        binding.setLifecycleOwner(this)


        return binding.root
    }

    fun insertNewDay(dayId: Int, timeWasted: Long, timeLimit: Long, application: String){
        val day = Day(dayId, timeWasted, timeLimit, application)
        // Add data to database
        mDayViewModel.addDay(day)
    }

    fun readAllData(): LiveData<List<Day>> {
        return mDayViewModel.readAllData()
    }


    /*
    override fun onResume(){
        super.onResume()
        Log.i("HomeFragment", "onResume Called")
    }
    */

    override fun onStart() {
        super.onStart()
        Log.i("HomeFragment", "onStart Called")

        mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)

        val queryStatsUtils: QueryStatsUtils = QueryStatsUtils()
        val topPck: UsageStats = queryStatsUtils.getTopPackage(context)

        var tWaste: Long = topPck.totalTimeInForeground
        var converted: Long = 0
        if (tWaste != null) {
            converted = (tWaste.toFloat() / 60000.toFloat()).toLong()
        }

        if (converted != null && converted > 0) {
            mDayViewModel.updateTodayTimeWasted(converted)
        }

        val topPckNme:String = topPck.packageName
        val packageName = topPckNme
        //val packageName = topPckNme?.substringAfterLast('.')
        packageName?.let { mDayViewModel.setPackageName(it) }



    }

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




    /*fun getIcon(): Drawable? {
        return activity?.packageManager?.getApplicationIcon("com.android.chrome")
    } */

    /*fun setIcon() {
        val icon = activity?.packageManager?.getApplicationIcon("com.android.chrome")
        val view: ImageView? = activity?.findViewById(R.id.app_icon)
        if (view != null) {
            view.setImageDrawable(icon)
        }
    } */
}


