package com.example.chronoboss

import android.app.Service

class MyService : Service {



}


public class MyService extends Service {
    BroadcastReceiver mReceiver;

    // use this as an inner class like here or as a top-level class
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // do something
        }

        // constructor
        public MyReceiver(){

        }
    }

    @Override
    public void onCreate() {
        // get an instance of the receiver in your service
        IntentFilter filter = new IntentFilter();
        filter.addAction("action");
        filter.addAction("anotherAction");
        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, filter);
    }
}