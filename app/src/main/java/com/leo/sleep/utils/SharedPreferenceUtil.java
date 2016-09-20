package com.leo.sleep.utils;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;

import com.leo.sleep.base.BaseApplication;

/**
 * Created by Leo on 2016/9/16.
 */
public class SharedPreferenceUtil {

    public static final String CITY_NAME="城市";
    public static final String HOUR="current_hour";

    public static final String CHANGE_ICONS = "change_icons";//切换图标
    public static final String CLEAR_CACHE = "clear_cache";//清空缓存
    public static final String AUTO_UPDATE = "change_update_time"; //自动更新时长
    public static final String NOTIFICATION_MODEL = "notification_model";
    public static final String ANIM_STRAT = "animation_start";

    public static int ONE_HOUR = 1000 * 60 * 60;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferenceUtil sInstance;

    public SharedPreferenceUtil(Context appContext) {
        sharedPreferences=appContext.getSharedPreferences("setting",Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(){
        if (sInstance==null){
            sInstance=new SharedPreferenceUtil(BaseApplication.appContext);
        }
        return sInstance;
    }

    public SharedPreferenceUtil putInt(String key,int value){
        sharedPreferences.edit().putInt(key,value).apply();
        return this;
    }

    public int getInt(String key,int defValue){
        return sharedPreferences.getInt(key,defValue);
    }

    public SharedPreferenceUtil putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public SharedPreferenceUtil putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    // 设置当前小时
    public void setCurrentHour(int h) {
        sharedPreferences.edit().putInt(HOUR, h).apply();
    }

    public int getCurrentHour() {
        return sharedPreferences.getInt(HOUR, 0);
    }

    // 图标种类相关
    public void setIconType(int type) {
        sharedPreferences.edit().putInt(CHANGE_ICONS, type).apply();
    }

    public int getIconType() {
        return sharedPreferences.getInt(CHANGE_ICONS, 0);
    }

    // 自动更新时间 hours
    public void setAutoUpdate(int t) {
        sharedPreferences.edit().putInt(AUTO_UPDATE, t).apply();
    }

    public int getAutoUpdate() {
        return sharedPreferences.getInt(AUTO_UPDATE, 3);
    }

    //当前城市
    public void setCityName(String name) {
        sharedPreferences.edit().putString(CITY_NAME, name).apply();
    }

    public String getCityName() {
        return sharedPreferences.getString(CITY_NAME, "广州");
    }

    //  通知栏模式 默认为常驻
    public void setNotificationModel(int t) {
        sharedPreferences.edit().putInt(NOTIFICATION_MODEL, t).apply();
    }

    public int getNotificationModel() {
        return sharedPreferences.getInt(NOTIFICATION_MODEL, Notification.FLAG_ONGOING_EVENT);
    }

    // 首页 Item 动画效果 默认关闭

    public void setMainAnim(boolean b) {
        sharedPreferences.edit().putBoolean(ANIM_STRAT, b).apply();
    }

    public boolean getMainAnim() {
        return sharedPreferences.getBoolean(ANIM_STRAT, false);
    }

}
