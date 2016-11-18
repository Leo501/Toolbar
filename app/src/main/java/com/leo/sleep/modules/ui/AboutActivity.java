package com.leo.sleep.modules.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leo70 on 2016/11/13.
 */

public class AboutActivity extends BaseActivity {

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }
}
