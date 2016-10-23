package com.leo.sleep.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.leo.sleep.R;

/**
 * Created by HugoXie on 16/6/25.
 *
 * Email: Hugo3641@gamil.com
 * GitHub: https://github.com/xcc3641
 * Info: 来源 brucezz 双击退出
 */
public class DoubleClickExit {
    /**
     * 双击退出检测, 阈值 1000ms
     */
    public static long lastClick = 0L;
    private static final int THRESHOLD = 2000;// 1000ms

    private static Snackbar snackbar;
    private static View snackBarView;
    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - lastClick < THRESHOLD;
        lastClick = now;
        return b;
    }
    public static Snackbar getSnackbar(View view,String s){
        snackbar=snackbar= Snackbar.make(view,s , Snackbar.LENGTH_SHORT)
                .setAction("Action", null);
        return snackbar;
    }

    public static void setSnackBarBg(Context context, int color){
        snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public static void setSnackbarTextColor(Context context,int color){
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context,color));
    }
}
