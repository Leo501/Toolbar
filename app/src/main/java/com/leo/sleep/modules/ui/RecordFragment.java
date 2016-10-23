package com.leo.sleep.modules.ui;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseFrament;
import com.leo.sleep.utils.PixelsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecordFragment extends BaseFrament implements SwipeRefreshLayout.OnRefreshListener{

    //butterknife
    private Unbinder unbinder;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipRefresh;
    private boolean isRefresh=false;

    public RecordFragment() {
    }

    @Override
    protected void lazyLoad() {

    }

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_record, container, false);
        unbinder=ButterKnife.bind(this,view);
        initSwipeRefresh();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initSwipeRefresh() {
        swipRefresh.setColorSchemeColors(Color.BLUE,Color.GREEN, Color.YELLOW,Color.RED);
        swipRefresh.setDistanceToTriggerSync(PixelsUtil.dp2px(getActivity(),100));
        swipRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (!isRefresh){
            isRefresh=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                    isRefresh=false;
                }
            },2000);
        }
    }

}
