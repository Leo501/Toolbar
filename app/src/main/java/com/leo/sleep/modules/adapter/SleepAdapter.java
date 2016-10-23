package com.leo.sleep.modules.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.sleep.R;
import com.leo.sleep.component.city.WeatherData;
import com.leo.sleep.modules.serializable.Weather;
import com.leo.sleep.utils.ChangeColorUtil;
import com.leo.sleep.utils.LogUtil;
import com.leo.sleep.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leo70 on 2016/10/15.
 */

public class SleepAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SleepAdapter";
    private Context context;
    private onLongClick longClick;
    private onItemClick itemClick;

    private static final int TYPE_ONE=0;//天气
    private static final int TYPE_TWO=1;//闹钟
    private static final int TYPE_THREE=2;//添加闹钟的图标

    //type1的数据源
    private Weather weather;
    private WeatherData weatherData;

    //通过构造函数来取得数据
    public SleepAdapter(Weather weather) {
        this.weather = weather;
    }

    public SleepAdapter(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public void setLongClick(onLongClick click){
        this.longClick=click;
    }

    public void setItemClick(onItemClick itemClick) {
        this.itemClick = itemClick;
    }

    //根据position不同的，来选择type.
    @Override
    public int getItemViewType(int position) {
        //
        if (position==SleepAdapter.TYPE_ONE){
            return SleepAdapter.TYPE_ONE;
        }
        if (position==SleepAdapter.TYPE_TWO){
            return SleepAdapter.TYPE_TWO;
        }
        if (position==SleepAdapter.TYPE_THREE){
            return SleepAdapter.TYPE_THREE;
        }
        return super.getItemViewType(position);
    }

    /**
     * 与上面的getItemViewType相对应，根据type来选择类型
     * @param parent 通过ViewGroup类型对象来取得view的布局
     * @param viewType 查看是哪种类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        switch (viewType){
            case TYPE_ONE:{//绑定view
               return new WeatherViewHolder(
                       LayoutInflater.from(context).inflate(R.layout.item_temperature,parent,false));
            }
        }
        return null;
    }

    //设置每个item的数据。
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType=getItemViewType(position);
        switch (itemType){
            case TYPE_ONE:{
                ((WeatherViewHolder)holder).bind(weatherData);
                holder.itemView.setOnClickListener(view->{itemClick.itemClick(""+itemType);});
                holder.itemView.setOnLongClickListener(v -> {longClick.longClick(""+itemType);return true;});
            }
        }
        super.onBindViewHolder(holder, position);
    }

    //要显示的个数
    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     * 通过继承RecyclerView.ViewHolder，把数据放到item中显示
     */
    class WeatherViewHolder extends RecyclerView.ViewHolder{

        //bind view
        @BindView(R.id.weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.temp_flu)
        TextView tempFlu;
        @BindView(R.id.temp_max)
        TextView tempMax;
        @BindView(R.id.temp_min)
        TextView tempMin;
        @BindView(R.id.dialog_city)
        TextView dialog_city;
        @BindView(R.id.temp_tomorrow_max)
        TextView temp_tomorrow_max;
        @BindView(R.id.temp_tomorrow_min)
        TextView temp_tomorrow_min;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void bind(WeatherData data){
            try {
                tempFlu.setText(String.format("%s℃",data.getTemp_now()));
                tempMax.setText(
                        String.format("↑ %s ℃", data.getTemp_today_max()));
                tempMin.setText(
                        String.format("↓ %s ℃", data.getTemp_today_min()));
                dialog_city.setText(Util.safeText(data.getCity()));
                temp_tomorrow_max.setText(String.format(" ~ %s℃",data.getTemp_today_max()));
                temp_tomorrow_min.setText(String.format("%s℃",data.getTemp_today_min()));
            }catch (Exception e){
                LogUtil.e(TAG,e.toString());
            }
        }

        /*void bind(Weather weather){
            try {
                tempFlu.setText(String.format("%s℃",weather.now.tmp));
                tempMax.setText(
                        String.format("↑ %s ℃", weather.dailyForecast.get(0).tmp.max));
                tempMin.setText(
                        String.format("↓ %s ℃", weather.dailyForecast.get(0).tmp.min));
                dialog_city.setText(Util.safeText(weather.basic.city));
            }catch (Exception e){
                LogUtil.e(TAG,e.toString());
            }
        }*/
    }
    //设置点击事件
    public interface onItemClick{
        void itemClick(String type);
    }
    public interface onLongClick{
        void longClick(String time);
    }
}
