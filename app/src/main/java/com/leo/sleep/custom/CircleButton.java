package com.leo.sleep.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.leo.sleep.R;

/**
 * Created by leo70 on 2016/12/5.
 */

public class CircleButton extends View {

    private Context context;
    public CircleButton(Context context) {
        this(context,null);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs);
    }
    protected void init(AttributeSet attrs){
        if (attrs==null){
            return;
        }
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
    }
}
