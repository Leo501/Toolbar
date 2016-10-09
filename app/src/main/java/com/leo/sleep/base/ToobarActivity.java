package com.leo.sleep.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.leo.sleep.R;

/**
 * 适配所有 toolbar
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
//      “v -> onToolbarClick()”运用了java8的新特征Lambda 是一个空方法

        toolbar.setOnClickListener(v -> onToolbarClick());
        setSupportActionBar(toolbar);
        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT>=21){
            appBarLayout.setElevation(10.6f);
        }
    }

    public boolean canBack() {
        return false;
    }

    protected void setAppBarAlpha(float alpha) {
        appBarLayout.setAlpha(alpha);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void hiderShowToolbar(){
        appBarLayout.animate()
                .translationY(isHidden?0:-appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isHidden=!isHidden;
    }

    protected void beforeSetContent() {

    }

    @Override
    protected void initViews(Bundle savedIntanceState) {
    }

}
