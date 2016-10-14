package com.wickeddevs.firebasechatrooms;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


public class MyApplication extends Application {

    public static MyApplication myApplication;

    @Override public void onCreate() {
        super.onCreate();
        myApplication = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
