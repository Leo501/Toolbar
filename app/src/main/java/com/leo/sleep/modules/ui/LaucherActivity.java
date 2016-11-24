package com.leo.sleep.modules.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.leo.sleep.R;
import com.leo.sleep.utils.FontManagerUtils;
import com.leo.sleep.utils.TransitionAnimUtils;
import com.leo.sleep.utils.VersionUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Created by leo70 on 2016/11/18.
 */

public class LaucherActivity extends RxAppCompatActivity {

    private static final String TAG = "LaucherActivity";
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private TextView version;
    private HTextView hTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        mContentView=findViewById(R.id.fullscreen_content_image);
        hTextView=(HTextView)findViewById(R.id.fullscreen_TextView);
        version=(TextView) findViewById(R.id.tv_version);
//        text.setVisibility(View.GONE);
        hTextView.setTypeface(FontManagerUtils.getInstance(getAssets()).getFont("fonts/PoiretOne-Regular.ttf"));
        hTextView.setAnimateType(HTextViewType.LINE);

        Observable.timer(3,TimeUnit.SECONDS)
                .compose(bindToLifecycle())//用于安全退订
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        TransitionAnimUtils.INSTANCE.makeCustomAnimation(
                                LaucherActivity.this,LaucherActivity.this
                                ,new Intent(LaucherActivity.this,MainActivity.class)
                                ,R.anim.activity_enter_anim,R.anim.activity_exit_anim
                        );
                        /*ActivityOptionsCompat options= ActivityOptionsCompat.makeCustomAnimation(
                                LaucherActivity.this,R.anim.activity_enter_anim,R.anim.activity_exit_anim);
                        Intent intent=new Intent(LaucherActivity.this,MainActivity.class);
                        ActivityCompat.startActivity(LaucherActivity.this,intent,options.toBundle());
                        finish();*/
                    }
                });
        /*new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                ActivityOptionsCompat options= ActivityOptionsCompat.makeCustomAnimation(
                        LaucherActivity.this,R.anim.activity_enter_anim,R.anim.activity_exit_anim);
                Intent intent=new Intent(LaucherActivity.this,MainActivity.class);
                ActivityCompat.startActivity(LaucherActivity.this,intent,options.toBundle());
                finish();
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: "+aLong);
            }
        }*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(0);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHidePartRunnable);
        mHideHandler.postDelayed(mHidePartRunnable, delayMillis);

    }

    /*public final Runnable hTextViewRunnable=new Runnable() {
        @Override
        public void run() {
            hTextView.animateText("Sleep");
            version.setText(VersionUtil.getVersion(LaucherActivity.this).toString());
            version.setVisibility(View.VISIBLE);
        }
    };*/
    public final Runnable mHidePartRunnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Hide UI first
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//            mHideHandler.postDelayed(hTextViewRunnable, 1000);
            Observable.timer(1000,TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {//只实现onNext
                        @Override
                        public void call(Long aLong) {
                            hTextView.animateText("Sleep");
                            version.setText(VersionUtil.getVersion(LaucherActivity.this).toString());
                            version.setVisibility(View.VISIBLE);
                        }
                    });
            /*new Subscriber<Long>() {
                //onCompleted() 和 onError() 二者是互斥的,只会执行两者之一
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Long aLong) {

                }
            }*/
        }
    };


//    public void onClick(View v) {
//        mCounter = mCounter >= sentences.length - 1 ? 0 : mCounter + 1;
//        hTextView.animateText(sentences[mCounter]);
//    }
}
