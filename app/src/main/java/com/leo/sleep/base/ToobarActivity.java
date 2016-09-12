package com.leo.sleep.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.leo.sleep.R;

/**
 * Created by Leo on 2016/9/11.
 */
public abstract class ToobarActivity extends BaseActivity {

    abstract protected int provideContentViewId();

    public void onToolbarClick(){

    }

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected boolean isHidden=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContent();
        setContentView(provideContentViewId());
        appBarLayout=(AppBarLayout) findViewById(R.id.appbar_layout);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        if (toolbar==null||appBarLayout==null){
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a toolbar.");
        }
    }

    protected void beforeSetContent() {

    }

    @Override
    protected void initViews(Bundle savedIntanceState) {

    }

    @Override
    protected void initToolBar() {
        toolbar.setOnClickListener(v -> onToolbarClick());
    }
}
