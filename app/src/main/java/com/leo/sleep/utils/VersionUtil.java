package com.leo.sleep.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.leo.sleep.R;

/**
 * Created by leo70 on 2016/11/13.
 */

public class VersionUtil {

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.not_find_version_name);
        }
    }

    /**
     * @return 应用版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 当前设备的系统版本号
     * @return
     */
    public static int getSystemVersion(){
        return Build.VERSION.SDK_INT;
    }


}
