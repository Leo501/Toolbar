package com.leo.sleep.modules.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseActivity;
import com.leo.sleep.component.city.Constant;
import com.leo.sleep.modules.adapter.HomePagerAdapter;
import com.leo.sleep.utils.CircularAnimUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener ,RecordFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    @BindView(R.id.viewPager)
    ViewPager viewPager;//左右切换Fragment,使页面滑动

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;//标签指示器与ViewPager组合使用

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.nav_view)
    NavigationView navigationView;//侧滑导航栏

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;//官方侧滑菜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        initPagerAdapter();
        initFloatingActionButton();
        initActionBarDrawerToggle();
    }

    private void initPagerAdapter() {
        HomePagerAdapter homePagerAdapter=new HomePagerAdapter(getSupportFragmentManager());
        homePagerAdapter.addTab(new RecordFragment(),"睡觉");
        homePagerAdapter.addTab(new RecordFragment(),"记录");
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    protected void initFloatingActionButton(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent=new Intent(MainActivity.this,ChoiceCityActivity.class);
                intent.putExtra(Constant.MULTI_CHECK,true);
                CircularAnimUtil.startActivity(MainActivity.this, intent, fab,
                        R.color.blue_greg_500);

            }
        });
    }

    protected void initActionBarDrawerToggle(){

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction: communication with fragment");
    }
}

