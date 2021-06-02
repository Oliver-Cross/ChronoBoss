package com.example.chronoboss

import android.app.usage.UsageStats
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.text.TextUtils.replace
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.chronoboss.database.Day
import com.example.chronoboss.database.DayViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var mDayViewModel: DayViewModel
    var testInt:Int = 1


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val icon: ImageView = view.findViewById(R.id.app_icon)
        val queryStatsUtils:QueryStatsUtils = QueryStatsUtils()
        val topPck:UsageStats? = queryStatsUtils.getTopPackage(context)
        val topPckNme:String? = topPck?.packageName
        val topPackageIcon: Drawable? = topPckNme?.let {
            activity?.packageManager?.getApplicationIcon(
                it
            )
        }

        val vw:TextView = view.findViewById(R.id.min_remain)
        if(testInt != null) {
            vw.setText(testInt.toString())
        }
        icon.setImageDrawable(topPackageIcon)

        mDayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)
        insertNewDay(100, 11, 70, "Play Store")
        val readAllData = readAllData()

        return view
    }

    fun insertNewDay(dayId: Int, timeWasted: Int, timeLimit: Int, application: String){
        val day = Day(dayId, timeWasted, timeLimit, application)
        // Add data to database
        mDayViewModel.addDay(day)
    }

    fun readAllData(): LiveData<List<Day>> {
        return mDayViewModel.readAllData()
    }

    /*override fun onStart() {
        super.onStart()
        testInt++
    } */









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


