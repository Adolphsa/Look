package com.zividig.look;

import android.app.Application;

/**
 * Created by adolph
 * on 2016-10-13.
 */

public class MyApplication extends Application {

    public static MyApplication myApplication;

    public static Application getContext() {

        return myApplication;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("application方法被调用");
        myApplication = this;
    }
}
