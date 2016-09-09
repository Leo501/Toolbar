package com.leo.sleep.modules.ui;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leo.sleep.R;
import com.leo.sleep.utils.PixelsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecordFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipRefresh;
    private boolean isRefresh=false;

    //用于与activity通信的
    private OnFragmentInteractionListener mListener;

    public RecordFragment() {
    }

    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    //    用于跟activity通信的接口
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
