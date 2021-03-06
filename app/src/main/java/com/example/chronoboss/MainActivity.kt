package com.example.chronoboss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.drawable.Icon
//import android.content.pm.PackageManager
//import android.graphics.drawable.Drawable
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.chronoboss.homeFragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//import android.widget.ImageView

/** main activity launched upon opening, host to home fragment
 */

class MainActivity : AppCompatActivity() {

    /** function called upon initial launch, adds home fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize navigation bar
        val tablayout: TabLayout = findViewById(R.id.tab_layout_navigation)
        val viewpager: ViewPager2 = findViewById(R.id.view_pager_nav)
        val fm: FragmentManager = supportFragmentManager
        val fragmentadapter: FragmentAdapter = FragmentAdapter(fm, lifecycle)
        viewpager.adapter = fragmentadapter
        var tabNames: ArrayList<String> = ArrayList()
        tabNames.add("Home")
        tabNames.add("Stats")
        tabNames.add("Progress")
        tabNames.add("Settings")

        var tabIcons: ArrayList<Int> = ArrayList()
        tabIcons.add(R.drawable.home_icon)
        tabIcons.add(R.drawable.stats_icon)
        tabIcons.add(R.drawable.progress_icon)
        tabIcons.add(R.drawable.settings_icon)

        TabLayoutMediator(tablayout, viewpager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.setText(tabNames[position])
                    tab.setIcon(tabIcons[position])
                }
            }).attach()

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewpager.setCurrentItem(tab.position)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        viewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tablayout.selectTab(tablayout.getTabAt(position))
            }
        })

    }

    /** function called upon activity resuming, begins polling service
     */
    override fun onResume(){
        super.onResume()
        startService(Intent( this, MakeItWork::class.java ) )
    }

    /** function called when activity paused, begins polling service
     *
     */
    override fun onPause(){
        super.onPause()
        startService(Intent( this, MakeItWork::class.java ))
    }

    /** function to open intent to query stats, for testing purposes
     *
     */
    fun goQueryStats(view: View) {
        val intent = Intent(this, QueryStatsActivity::class.java)
        startActivity(intent)
    }
}