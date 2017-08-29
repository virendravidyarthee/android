package com.virendra.mpd.meshcom;

import android.app.Application;
import com.squareup.otto.Bus;


public class BusApplication extends Application {
    private static Bus myBus = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static Bus getBus(){
        if(myBus == null){
            myBus = new Bus();
        }
        return myBus;
    }



}
