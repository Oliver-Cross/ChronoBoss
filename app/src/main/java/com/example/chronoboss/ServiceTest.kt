package com.example.chronoboss

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

class ServiceTest : Service() {
    private lateinit var mHandler: Handler
    final val DEFAULT_SYNC_INTERVAL:Long = 30*1000

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    val runnableService:Runnable = object : Runnable {
        override fun run() {
            val message:String = "test my code"
        }

            mHandler.postDelayed(this, DEFAULT_SYNC_INTERVAL)



    }


}
        // task to be run here
        /**private Runnable runnableService = new Runnable() {
            @Override
            public void run() {
                syncData();
                // Repeat this runnable code block again every ... min
                mHandler.postDelayed(runnableService, Constant.DEFAULT_SYNC_INTERVAL);
            }
        };

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            // Create the Handler object
            mHandler = new Handler();
            // Execute a runnable task as soon as possible
            mHandler.post(runnableService);

            return START_STICKY;
        }

        private synchronized void syncData() {
            // call your rest service here
        }
    }
} */