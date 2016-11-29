package com.leo.sleep.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        if (Build.VERSION.SDK_INT>=16){
            ActivityOptionsCompat options= ActivityOptionsCompat.makeCustomAnimation(
                    context,enterResId,exitResId);
            ActivityCompat.startActivity(activity,intent,options.toBundle());
        }else {
            activity.startActivity(intent);
        }
    }

    public void delayTransition(RxAppCompatActivity activity, Context context, int time, Intent intent){
        Observable.timer(time, TimeUnit.MILLISECONDS)
                .compose(activity.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        activity.startActivity(intent);
                    }
                });
    }
}
