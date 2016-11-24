package com.leo.sleep.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * Created by leo70 on 2016/11/24.
 */

public enum TransitionAnimUtils {
    INSTANCE;

    /**
     *
     * @param context 启动上下文
     * @param activity 启动activity
     * @param intent
     * @param enterResId
     * @param exitResId
     */
    public void makeCustomAnimation(Context context, Activity activity, Intent intent, int enterResId, int exitResId ){
        ActivityOptionsCompat options= ActivityOptionsCompat.makeCustomAnimation(
                context,enterResId,exitResId);
        ActivityCompat.startActivity(activity,intent,options.toBundle());
        activity.finish();
    }
}
