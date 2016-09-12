package com.leo.sleep.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Leo on 2016/9/11.
 */
public class BaseApplication extends Application {

    public static Context appContext=null;
    public static String cacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();

    }

    public static Context getAppContext(){
        return appContext;
    }
}
