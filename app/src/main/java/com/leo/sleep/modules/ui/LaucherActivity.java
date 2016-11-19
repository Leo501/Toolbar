package com.leo.sleep.modules.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leo.sleep.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by leo70 on 2016/11/18.
 */

public class LaucherActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_coordinator);
    }
}
