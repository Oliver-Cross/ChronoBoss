package com.example.chronoboss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //this.setIcon()
    }

    fun getIcon():Drawable{
        return packageManager.getApplicationIcon("com.android.chrome")
    }

    fun setIcon() {
        val view: ImageView = findViewById(R.id.app_icon)
        view.setImageDrawable(getIcon())
    }

    fun goQueryStats(view:View) {
        val intent = Intent(this, QueryStatsFragment::class.java)
        startActivity(intent)
    }


}