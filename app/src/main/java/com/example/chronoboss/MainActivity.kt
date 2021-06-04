package com.example.chronoboss

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.drawable.Icon
//import android.content.pm.PackageManager
//import android.graphics.drawable.Drawable
import android.view.View
import android.webkit.WebViewFragment
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.chronoboss.homeFragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_settings.*

//import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    lateinit var cont:Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cont = this.applicationContext
        setContentView(R.layout.activity_main)
        /*val settings_fragment = supportFragmentManager.findFragmentById(R.id.settingsFragment)
        var app_budget = settings_fragment?.option_app_budget_slider?.progress
        val serviceIntent:Intent = Intent(this, MakeItWork::class.java)
        serviceIntent.putExtra("varTest", app_budget) */
        //startService(Intent( this, MakeItWork::class.java ) )

        //Initialize navigation bar
        /*
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_controller) as NavHostFragment
        val navController = navHostFragment.navController
        */
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


        // val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        // navigationView.setupWithNavController(navController)

    }

    /*fun getIcon():Drawable{
        return packageManager.getApplicationIcon("com.android.chrome")
    }

    fun setIcon() {
        val view: ImageView = findViewById(R.id.app_icon)
        view.setImageDrawable(getIcon())
    } */

    fun getContNow(): Context {
        return cont
    }
    fun goQueryStats(view: View) {
        val intent = Intent(this, QueryStatsActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {

        super.onStart()
        val settings_fragment = supportFragmentManager.findFragmentById(R.id.settingsFragment)
        var app_budget = settings_fragment?.option_app_budget_slider?.progress
        //Toast.makeText(cont, "is this set to anything: " + app_budget.toString(), Toast.LENGTH_SHORT).show()
        val serviceIntent:Intent = Intent(this, MakeItWork::class.java)
        serviceIntent.putExtra("varTest", app_budget)
        startService(Intent( this, MakeItWork::class.java ) )
    }
}