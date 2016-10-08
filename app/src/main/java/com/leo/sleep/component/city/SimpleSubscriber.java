package com.leo.sleep.component.city;

import com.leo.sleep.utils.LogUtil;

import rx.Subscriber;

/**
 * Created by leo70 on 2016/10/5.
 */

public abstract class SimpleSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(e.toString());
    }
}
