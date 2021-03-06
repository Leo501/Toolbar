package com.leo.sleep.modules.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseActivity;
import com.leo.sleep.component.city.Constant;
import com.leo.sleep.modules.adapter.HomePagerAdapter;
import com.leo.sleep.utils.CircularAnimUtil;
import com.leo.sleep.utils.DoubleClickExit;
import com.leo.sleep.utils.SnackbarUtils;
import com.leo.sleep.utils.TransitionAnimUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.coord)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;//左右切换Fragment,使页面滑动

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;//标签指示器与ViewPager组合使用

//    @BindView(R.id.fab)
//    FloatingActionButton fab;

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
        homePagerAdapter.addTab(new SleepFragment(MainActivity.this,getFragmentManager()),"睡觉");
        homePagerAdapter.addTab(new RecordFragment(),"记录");
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(pageListener);
    }


    protected void initFloatingActionButton(){
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ChoiceCityActivity.class);
                intent.putExtra(Constant.MULTI_CHECK,true);
                CircularAnimUtil.startActivity(MainActivity.this, intent, fab,
                        R.color.blue_greg_500);

            }
        });*/
    }

    protected void initActionBarDrawerToggle(){

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                SnackbarUtils.showShortSnackbar(coordinatorLayout,"再按一次退出",
                        ContextCompat.getColor(getApplication(),R.color.white),
                        ContextCompat.getColor(getApplication(),R.color.red_light));
            } else {
                finish();
            }
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
            Intent intent=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(MainActivity.this,LaucherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(MainActivity.this,ChoiceCityActivity.class);
            TransitionAnimUtils.INSTANCE.delayTransition(MainActivity.this,MainActivity.this
            ,300,intent);
            /*Observable.timer(300, TimeUnit.MILLISECONDS)
                    .compose(bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            startActivity(intent);
                        }
                    });*/


        } else if (id == R.id.nav_manage) {
            Intent intent=new Intent(MainActivity.this,FullscreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*ViewPager.OnPageChangeListener pageListener=new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //换页设置是否显示
        @Override
        public void onPageSelected(int position) {
            if (position==1){
                fab.hide();
            }else {
                fab.show();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };*/

}

