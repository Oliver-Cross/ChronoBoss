package com.example.chronoboss

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class PollingService : Service() {
    val ACTION:String = "com.example.chronoboss.PollingService"
    //private lateinit var mNotification: Notification
    //private lateinit var mManager:NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    /*override fun onCreate(){
        initNotifiManager()
    } */

    override fun onStart(intent: Intent?, startId:Int){
        PollingThread().start()
    }




    class PollingThread : Thread(){
        var count:Int = 0
        override fun run(){
            //query usage stats
        }

    }

    /**class PollingThread extends Thread {


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
} */




}

/**public class PollingService extends Service {

    //Pop up Notification
    private void showNotification() {
        mNotification.when = System.currentTimeMillis();
        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this, MessageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
        Intent.FLAG_ACTIVITY_NEW_TASK);
        mNotification.setLatestEventInfo(this,
            getResources().getString(R.string.app_name), "You have new message!", pendingIntent);
        mManager.notify(0, mNotification);
    }
    /**
     * Polling thread
     * Simulate an asynchronous thread polling the Server
     * @Author Ryan
     * @Create  2013-7-13 10:18:34 AM
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count ++;
            //When the count is divisible by 5, a notification pops up
            if (count % 5 == 0) {
                showNotification();
                System.out.println("New message!");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }
} */