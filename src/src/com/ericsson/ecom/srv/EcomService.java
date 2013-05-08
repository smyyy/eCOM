package com.ericsson.ecom.srv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.ericsson.ecom.app.EcomApplication;

public class EcomService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Preferences | File Templates.
    }


    public EcomApplication getApp(){
        return (EcomApplication) getApplication();
    }
}
