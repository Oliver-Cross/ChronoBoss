package com.example.chronoboss

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class MakeItWork : Service() {
    private var mHandler: Handler? = null

    // task to be run here
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            val message = "hi"
            // Repeat this runnable code block again every ... min
            mHandler!!.postDelayed(this, DEFAULT_SYNC_INTERVAL)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Create the Handler object
        mHandler = Handler()
        // Execute a runnable task as soon as possible
        mHandler!!.post(runnableService)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        // default interval for syncing data
        const val DEFAULT_SYNC_INTERVAL = (30 * 1000).toLong()
    }
}