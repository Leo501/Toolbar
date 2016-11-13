package com.leo.sleep.modules.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.sleep.R;

import java.util.ArrayList;

/**
 * Created by Leo on 2016/9/23.
 */
public class CityAdapter extends AnimRecyclerViewAdapter<CityAdapter.CityViewHolder> {

    private Context context;
    private ArrayList<String> dataList;
    private OnRecyclerViewItemClickListener onItemClickListener=null;

    public CityAdapter(Context context, ArrayList<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //查看系统版本号，选择不同的布局
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            return new CityViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city_low_version,parent,false));
        }
            return new CityViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city,parent,false));
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(dataList.get(position));
        holder.cardView.setOnClickListener(v->onItemClickListener.onItemClick(v,position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }

    class CityViewHolder extends RecyclerView.ViewHolder{

        TextView itemCity;
        CardView cardView;
        public CityViewHolder(View itemView) {
            super(itemView);
            itemCity=(TextView)itemView.findViewById(R.id.item_city);
            cardView=(CardView) itemView.findViewById(R.id.cardView);
        }
        public void bind(String name){
            itemCity.setText(name);
        }

    }
}
