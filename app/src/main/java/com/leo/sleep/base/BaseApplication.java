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
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }

    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static Context getAppContext(){
        return appContext;
    }
}
