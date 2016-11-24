package com.leo.sleep.modules.ui;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseFrament;
import com.leo.sleep.component.city.AlarmClock;
import com.leo.sleep.component.city.WeatherData;
import com.leo.sleep.component.manager.DBManager;
import com.leo.sleep.component.manager.DataBaseOrm;
import com.leo.sleep.component.model.AlarmsModel;
import com.leo.sleep.modules.adapter.SleepAdapter;
import com.leo.sleep.modules.serializable.Weather;
import com.leo.sleep.utils.SharedPreferenceUtil;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;

public class SleepFragment extends BaseFrament implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SleepFragment";
    private Context context;
    private Unbinder unbinder;
    private SleepFragment frag;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_erro)
    ImageView imageViewErro;

    //用于加载timer控件
    private FragmentManager manager;

    private SleepAdapter adapter;
    //闹钟列表
    List<AlarmsModel> list=new ArrayList<>();

    public SleepFragment(Context context) {
        this.frag=this;
        this.context = context;
    }

    public SleepFragment(Context context, FragmentManager manager) {
        this.frag=this;
        this.context = context;
        this.manager = manager;
    }

    //基类方法
    @Override
    protected void lazyLoad() {

    }
    //基类继承的方法
    @Override
    public void onRefresh() {

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
        context.registerReceiver(timeRefreshReceiver,new IntentFilter(Intent.ACTION_TIME_TICK));
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

        adapter=new SleepAdapter(data,list);
        adapter.setLongClick(new SleepAdapter.onLongClick() {
            @Override
            public void longClick(AlarmsModel alarm) {
                isDeleteItem(alarm.getId());
            }
        });
        //
        adapter.setAlarmListener(new SleepAdapter.onAddAlarmClick() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(
                        addTimeListenr,now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),true
                );
                timePickerDialog.show(manager,"timer");

            }
        });
        //添加修改时间的onClick
        adapter.setItemClick(new SleepAdapter.onItemClick() {
            @Override
            public void itemClick(AlarmsModel alarm) {
                //通过alarm来取得id
                AlarmsModel alarmOld=DataBaseOrm.INSTANCE.queryById(alarm.getId(),AlarmsModel.class);
                TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                                alarmOld.setHours(hourOfDay);
                                alarmOld.setMinutes(minute);
                                alarmOld.setAM(alarmOld.getAM());
                                DataBaseOrm.INSTANCE.upDate(alarmOld);
                                loadingAlarm(true);
                            }
                        },alarmOld.getHours(),
                        alarmOld.getMinutes(),true
                );
                timePickerDialog.show(manager,"timer");
            }
        });
        recyclerView.setAdapter(adapter);

        /*DataBaseOrm.INSTANCE.delete(AlarmsModel.class);*/
        loadingAlarm(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context.unregisterReceiver(timeRefreshReceiver);
    }

    private void load(){

    }

    private TimePickerDialog.OnTimeSetListener addTimeListenr =new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            AlarmsModel alarms=new AlarmsModel(true,hourOfDay,minute);
            DataBaseOrm.INSTANCE.save(alarms);
            loadingAlarm(false);
        }
    };

    //刷新闹钟
    private void loadingAlarm(boolean isAll){

        List<AlarmsModel> rawlist=DataBaseOrm.INSTANCE.queryAll(AlarmsModel.class);
        list.clear();
        for (AlarmsModel alarm:rawlist){
            Log.d(TAG, "loadingAlarm: id="+alarm.getId());
            list.add(alarm);
        }
        adapter.notifyDataSetChanged();
    }

    private void isDeleteItem(int id) {
        new AlertDialog.Builder(context).setMessage("您要删除该闹钟么？"
        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBaseOrm.INSTANCE.delete(DataBaseOrm.INSTANCE.queryById(id,AlarmsModel.class));
                loadingAlarm(true);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //用于刷新时间的广播
    private BroadcastReceiver timeRefreshReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())){
                //用于更新时间
                Observable.just(1)
                        .observeOn(AndroidSchedulers.mainThread())//在ui线程
                        .compose(frag.bindToLifecycle())//用于安全退订
                        .subscribe( (i)->{adapter.notifyDataSetChanged();});
            }
        }
    };


}
