package com.leo.sleep.modules.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.leo.sleep.R;
import com.leo.sleep.base.ToobarActivity;
import com.leo.sleep.component.city.Constant;
import com.leo.sleep.component.city.OrmLite;
import com.leo.sleep.component.city.SimpleSubscriber;
import com.leo.sleep.component.table.CityORM;
import com.leo.sleep.modules.adapter.CityAdapter;
import com.leo.sleep.modules.db.DBManager;
import com.leo.sleep.modules.db.CityDB;
import com.leo.sleep.modules.serializable.City;
import com.leo.sleep.modules.serializable.Province;
import com.leo.sleep.utils.LogUtil;
import com.leo.sleep.utils.RxUtils;
import com.leo.sleep.utils.SharedPreferenceUtil;
import com.leo.sleep.utils.SimpleSubscriberUtil;
import com.leo.sleep.utils.Util;

import java.util.ArrayList;
import java.util.List;

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
        Observable.defer(()->{
            DBManager.getInstance().openDatabase();
            return Observable.just(1);
        }).compose(RxUtils.rxSchedulerHelper())
                //解决RxJava内存泄露问题
                .compose(this.bindToLifecycle())
                .subscribe(integer -> {
                //对recyclerView进行初始化跟设置点击事件
                initRecyclerView();
                //开始选择省份
                queryProvinces();

        });

        Intent intent=getIntent();
        isChecked=intent.getBooleanExtra(Constant.MULTI_CHECK,false);
        if (isChecked&&SharedPreferenceUtil.getInstance().getBoolean("Tips",true)){
            showTips();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.getInstance().closeDatabase();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (currentLevel==LEVEL_PROVINCE){
            finish();
        }else {
            queryProvinces();
            recyclerView.smoothScrollToPosition(0);
        }
    }

    private void showTips() {
        new AlertDialog.Builder(this).setTitle("多城市管理模式").setMessage("您现在是多城市管理模式,直接点击即可新增城市.如果暂时不需要添加,"
                + "在右上选项中关闭即可像往常一样操作.\n因为 api 次数限制的影响,多城市列表最多三个城市.(๑′ᴗ‵๑)"
        ).setPositiveButton("明白", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("不再提示", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtil.getInstance().putBoolean("Tips", false);
            }
        }).show();
    }

    private void initView() {
        if (progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //FadeInUpAnimator为第三方库
//        recyclerView.setItemAnimator(new FadeInUpAnimator());
        adapter=new CityAdapter(this,dataList);
        recyclerView.setAdapter(adapter);

        //设置点击事件，如果选择完城市后。finish退出。
        adapter.setOnItemClickListener(((view, pos) -> {
            if (currentLevel==LEVEL_PROVINCE){
                selectedProvince=provincesList.get(pos);
                recyclerView.smoothScrollToPosition(0);
                queryCities();
            }else if (currentLevel==LEVEL_CITY){
                String city= Util.replaceCity(cityList.get(pos).cityName);
                LogUtil.d(city);
                if (isChecked){
                    //保存选择城市
                    OrmLite.getInstance().save(new CityORM(city));
//                    RxBus.getDefault().post(new MultiUpdate());
                }else {
                    SharedPreferenceUtil.getInstance().setCityName(city);
//                    RxBus.getDefault().post(new ChangeCityEvent());
                }

                finish();
            }
        }));
    }

    private void queryProvinces(){
        getToolbar().setTitle("选择省份");
        Observable.defer(()->{
           if (provincesList.isEmpty()){
               provincesList.addAll(CityDB.loadProvinces(DBManager.getInstance().getDatabase()));
           }
            dataList.clear();
            return Observable.from(provincesList);
        }).map(province -> province.proName)
        .toList()
        .compose(RxUtils.rxSchedulerHelper())
        .compose(this.bindToLifecycle())
        .doOnTerminate(()->progressBar.setVisibility(View.GONE))
        .doOnCompleted(new Action0() {
            //先执行.subscriber中的onNext函数,再执行.doOnCompleted()中的call()函数
            @Override
            public void call() {
                currentLevel=LEVEL_PROVINCE;
                adapter.notifyDataSetChanged();
                //定位到第一个item
//                recyclerView.smoothScrollToPosition(0);
            }
        })//先执行.subscriber中的onNext函数,再执行.doOnCompleted()中的call()函数
        .subscribe(new SimpleSubscriber<List<String>>() {
            @Override
            public void onNext(List<String> strings) {
                dataList.addAll(strings);
            }
        });
    }

    private void queryCities() {
        getToolbar().setTitle("选择城市");
        dataList.clear();
        adapter.notifyDataSetChanged();
        //加载城市列表数据
        Observable.defer(()->{
            cityList= CityDB.loadCities(DBManager.getInstance().getDatabase(),
                    selectedProvince.proSort);
            return Observable.from(cityList);
        })
            .map(city -> city.cityName)
            .toList()
            .compose(RxUtils.rxSchedulerHelper())
            .compose(this.bindToLifecycle())//用于管理Observable
            .doOnCompleted(new Action0() {
                @Override
                public void call() {
                    currentLevel=LEVEL_CITY;
                    adapter.notifyDataSetChanged();
                    //定位到每一个item
                    recyclerView.smoothScrollToPosition(0);
                }
            })//先执行subscribe,再执行doOnCompleted
            .subscribe(new SimpleSubscriberUtil<List<String>>() {
                @Override
                public void onNext(List<String> strings) {
                    dataList.addAll(strings);
                }
            });
    }


}
