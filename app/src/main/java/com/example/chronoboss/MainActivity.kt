package com.example.chronoboss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.drawable.Drawable
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

//import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize navigation bar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_controller) as NavHostFragment
        val navController = navHostFragment.navController

        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        navigationView.setupWithNavController(navController)

    }

    /*fun getIcon():Drawable{
        return packageManager.getApplicationIcon("com.android.chrome")
    }

    fun setIcon() {
        val view: ImageView = findViewById(R.id.app_icon)
        view.setImageDrawable(getIcon())
    } */

    fun goQueryStats(view:View) {
        val intent = Intent(this, QueryStatsActivity::class.java)
        startActivity(intent)
    }
}