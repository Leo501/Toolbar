package com.leo.sleep.modules.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/9/7.
 */
public class HomePagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> titles=new ArrayList<>();
    private TabLayout tabLayout;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomePagerAdapter(FragmentManager fm, TabLayout tabLayout){
        super(fm);
        this.tabLayout=tabLayout;
    }

    public void addTab(Fragment fragment,String title){
        this.fragments.add(fragment);
        this.titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
