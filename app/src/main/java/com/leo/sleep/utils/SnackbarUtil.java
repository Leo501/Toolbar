package com.leo.sleep.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by leo70 on 2016/11/9.
 */

public enum  SnackbarUtil {

    INSTANCE;

    public Snackbar getSnackbar(View view,String s){
        Snackbar snackbar= Snackbar.make(view,s , Snackbar.LENGTH_SHORT)
                .setAction("Action", null);
        return snackbar;
    }

    public void setSnackBarBg(Context context, Snackbar snackbar,int color){
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public static void setSnackbarTextColor(Context context,Snackbar snackbar,int color){
        View snackBarView=snackbar.getView();
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context,color));
    }

}
