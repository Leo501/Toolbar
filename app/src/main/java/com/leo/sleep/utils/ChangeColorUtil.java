package com.leo.sleep.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * 改变图片的颜色
 * Created by leo70 on 2016/10/23.
 */

public class ChangeColorUtil {

    public static void setColor(Context context, ImageView view,int colorId){
        final int newColor= ContextCompat.getColor(context,colorId);
        view.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
    }
}
