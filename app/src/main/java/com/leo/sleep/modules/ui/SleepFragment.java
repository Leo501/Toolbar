package com.leo.sleep.modules.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseFrament;
import com.leo.sleep.component.city.WeatherData;
import com.leo.sleep.modules.adapter.SleepAdapter;
import com.leo.sleep.modules.serializable.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SleepFragment extends BaseFrament implements SwipeRefreshLayout.OnRefreshListener{

    private Unbinder unbinder;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_erro)
    ImageView imageViewErro;

    private SleepAdapter adapter;
    private static Weather weather=new Weather();

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sleep, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        if (swipeRefresh!=null){
            swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swipeRefresh.setOnRefreshListener(()->{
                swipeRefresh.postDelayed(this::load,1000);
            });
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        WeatherData data=new WeatherData("","广州",20,23,16,23,17);
        adapter=new SleepAdapter(data);
        adapter.setItemClick(new SleepAdapter.onItemClick() {
            @Override
            public void itemClick(String type) {
                Snackbar.make(getView(), "已经将" + type + "删掉了 Ծ‸ Ծ", Snackbar.LENGTH_LONG).setAction("撤销",
                        view1 -> {
                        }).show();
            }
        });
        adapter.setLongClick(new SleepAdapter.onLongClick() {
            @Override
            public void longClick(String time) {
                Snackbar.make(getView(), "已经将" + time + "删掉了 Ծ‸ Ծ", Snackbar.LENGTH_LONG).setAction("撤销",
                        view1 -> {
                        }).show();
            }
        });
        adapter.setAlarmListener(new SleepAdapter.onAlarmClick() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"设置闹钟" , Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onRefresh() {

    }

    private void load(){

    }
}
