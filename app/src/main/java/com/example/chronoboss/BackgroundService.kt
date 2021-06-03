package com.example.chronoboss

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import kotlin.properties.Delegates

class BackgroundService : Service() {

    private var isRunning by Delegates.notNull<Boolean>()
    private lateinit var backgroundThread:Thread
    private lateinit var context:Context

    override fun onBind(intent: Intent):IBinder?{
        return null
    }

    private val myTask:Runnable = Runnable (){
        fun run(){
            Toast.makeText(context, "service is running", Toast.LENGTH_LONG)
            //stopSelf()
        }
    }

    override fun onCreate() {
        this.context = this
        this.isRunning = false
        this.backgroundThread = Thread(myTask)


    }

    override fun onDestroy() {
        this.isRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isRunning){
            this.isRunning = true
            this.backgroundThread.start()
        }
        return START_STICKY
    }

}