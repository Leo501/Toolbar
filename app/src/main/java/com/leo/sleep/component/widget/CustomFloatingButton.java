package com.leo.sleep.component.widget;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Created by leo70 on 2016/10/23.
 */

public class CustomFloatingButton extends FloatingActionButton {

    private Context context;
    public CustomFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public CustomFloatingButton(Context context) {
        super(context);
        this.context=context;
    }

}
