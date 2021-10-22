package com.news2day.Activity;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by Ravi Tamada on 15/06/16.
 * www.androidhive.info
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
//        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
//        appSignatureHelper.getAppSignatures();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

//    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
//        ConnectivityReceiver.connectivityReceiverListener = listener;
//    }
}
