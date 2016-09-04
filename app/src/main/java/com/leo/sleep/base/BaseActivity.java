package com.leo.sleep.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Leo on 2016/9/4.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void initViews(Bundle savedIntanceState);
    protected abstract void initToolBar();
}
