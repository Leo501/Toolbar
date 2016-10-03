package com.leo.sleep.modules.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.leo.sleep.R;
import com.leo.sleep.base.ToobarActivity;
import com.leo.sleep.modules.adapter.CityAdapter;
import com.leo.sleep.modules.db.DBManager;
import com.leo.sleep.modules.db.WeatherDB;
import com.leo.sleep.modules.serializable.City;
import com.leo.sleep.modules.serializable.Province;
import com.leo.sleep.utils.RxUtils;
import com.leo.sleep.utils.SimpleSubscriberUtil;
import com.leo.sleep.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.internal.Utils;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import rx.Observable;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action0;

/**
 * Created by Leo on 2016/9/18.
 */
public class ChoiceCityActivity extends ToobarActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ArrayList<String> dataList =new ArrayList<>();
    private Province selectedProvince;
    private City selectedCity;
    private List<Province> provincesList =new ArrayList<>();
    private List<City> cityList;
    private CityAdapter adapter;

    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    private int currentLevel;

    private boolean isChecked = false;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_choice_city;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        addSubscription(Observable.defer(()->{
            DBManager.getInstance().openDatabase();
            return Observable.just(LEVEL_PROVINCE);
        }).compose(RxUtils.rxSchedulerHelper())
        .subscribe(integer -> {
            initRecyclerView();
        }));
    }

    private void initView() {
        if (progressBar==null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //FadeInUpAnimator为第三方库
        recyclerView.setItemAnimator(new FadeInUpAnimator());
        adapter=new CityAdapter(this,dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(((view, pos) -> {
            if (currentLevel==LEVEL_PROVINCE){
                selectedProvince=provincesList.get(pos);
                recyclerView.smoothScrollToPosition(0);
                queryCities();
            }else if (currentLevel==LEVEL_CITY){
                String city= Util.replaceCity(cityList.get(pos).cityName);
                if (isChecked){

                }
            }
        }));
    }

    private void queryCities() {
        getToolbar().setTitle("选择城市");
        dataList.clear();
        adapter.notifyDataSetChanged();
        addSubscription(Observable.defer(()->{
            cityList= WeatherDB.loadCities(DBManager.getInstance().getDatabase(),
                    selectedProvince.proSort);
            return Observable.from(cityList);
        })
            .map(city -> city.cityName)
            .toList()
            .compose(RxUtils.rxSchedulerHelper())
            .doOnCompleted(new Action0() {
                @Override
                public void call() {
                    currentLevel=LEVEL_CITY;
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(0);
                }
            })
            .subscribe(new SimpleSubscriberUtil<List<String>>() {
                @Override
                public void onNext(List<String> strings) {
                    dataList.addAll(strings);
                }
            }));
    }


}
