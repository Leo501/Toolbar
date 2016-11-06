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
import com.leo.sleep.component.city.AlarmClock;
import com.leo.sleep.component.city.WeatherData;
import com.leo.sleep.modules.serializable.Weather;
import com.leo.sleep.utils.ChangeColorUtil;
import com.leo.sleep.utils.LogUtil;
import com.leo.sleep.utils.Util;
import com.zcw.togglebutton.ToggleButton;

import java.util.List;

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
    private onAddAlarmClick alarmListener;

    private static final int TYPE_ONE=0;//天气
    private static final int TYPE_TWO=1;//添加闹钟的图标
    private static final int TYPE_THREE=2;//闹钟

    //type的数据源
    private Weather weather;
    private WeatherData weatherData;
    private List<AlarmClock> alarmList;
    private int typeThreePos=0;

    //通过构造函数来取得数据
    public SleepAdapter(Weather weather) {
        this.weather = weather;
    }

    public SleepAdapter(WeatherData weatherData,List<AlarmClock> list) {
        this.weatherData = weatherData;
        this.alarmList=list;
        typeThreePos=(alarmList!=null?alarmList.size():0)+1;
    }

    public void setLongClick(onLongClick click){
        this.longClick=click;
    }

    public void setItemClick(onItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setAlarmListener(onAddAlarmClick alarmListener) {
        this.alarmListener = alarmListener;
    }

    /**
     * 根据position不同的，来选择type.
     * @param position 位置
     * @return  返回类型
     */
    @Override
    public int getItemViewType(int position) {

        if (position==SleepAdapter.TYPE_ONE){
            return SleepAdapter.TYPE_ONE;
        }else if (position==typeThreePos){
            return SleepAdapter.TYPE_THREE;
        }else {
            return SleepAdapter.TYPE_TWO;
        }
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
            case TYPE_THREE:{
                return new AlarmIconViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.item_add_alarm,parent,false));
            }
            case TYPE_TWO:{
                return new AlarmTimerViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.item_alarm,parent,false));
            }
        }
        return null;
    }

    /**
     * 设置每个item的数据。
     * @param holder  用于绷定数据
     * @param position 根据位置的不同来设置不同的类型的holder
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType=getItemViewType(position);
        switch (itemType){
            case TYPE_ONE:{
                ((WeatherViewHolder)holder).bind(weatherData);
                holder.itemView.setOnClickListener(view->{itemClick.itemClick(""+itemType);});
                holder.itemView.setOnLongClickListener(v -> {longClick.longClick(""+itemType);return true;});
                break;
            }
            case TYPE_THREE:{
                holder.itemView.setOnClickListener(v -> {alarmListener.onClick(v);});
                break;
            }
            case TYPE_TWO:{
                ((AlarmTimerViewHolder)holder).bind(alarmList.get(position-1));
                break;
            }
            default:
                break;
        }

    }

    /**
     * @return 要显示的个数
     */
    @Override
    public int getItemCount() {
        int size=alarmList!=null?alarmList.size():0;
        return 2+size;
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
//                ChangeColorUtil.setColor(context,weatherIcon,android.R.color.background_dark);
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
    }

    /**
     * 添加闹钟类
     */
    class AlarmIconViewHolder extends RecyclerView.ViewHolder {

        private TextView alarmText;
        public AlarmIconViewHolder(View itemView) {
            super(itemView);
            alarmText=(TextView) itemView.findViewById(R.id.add_alarm);
        }
    }

    /**
     * 闹钟类
     */
    class AlarmTimerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_alarm_timer)
        TextView timer;
        @BindView(R.id.item_alarm_day)
        TextView day;
        @BindView(R.id.item_alarm_rest_time)
        TextView rest_time;
        @BindView(R.id.item_alarm_switch)
        ToggleButton button;

        public AlarmTimerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void bind(AlarmClock data){
            try{
                timer.setText(data.getTimer());
                day.setText(data.isAM()?"上午":"下午");
                rest_time.setText("未开启");
                //设置开关按键
                button.setToggleOff();
                button.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
                    @Override
                    public void onToggle(boolean on) {
                        data.setOpen(on);
                        if (data.isOpen()){
                            rest_time.setText(data.getTestTimer());
                        }else {
                            rest_time.setText("未开启");
                        }
                    }
                });
            }catch (Exception e){
                LogUtil.e(TAG,e.toString());
            }
        }
    }

    //设置Temperature的item点击事件
    public interface onItemClick{
        void itemClick(String type);
    }
    //设置Temperature的item长按事件
    public interface onLongClick{
        void longClick(String time);
    }
    //设置Alarm的点击事件。
    public interface onAddAlarmClick{
        void onClick(View view);
    }
}
