package com.leo.sleep.modules.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.leo.sleep.R;
import com.leo.sleep.base.ToobarActivity;
import com.leo.sleep.modules.serializable.City;
import com.leo.sleep.modules.serializable.Province;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_choice_city;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }
}
