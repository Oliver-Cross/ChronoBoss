package com.example.chronoboss

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TestAlarm :BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val background:Intent = Intent(context, BackgroundService::class.java)
        context?.startService(background)
        
    }

}