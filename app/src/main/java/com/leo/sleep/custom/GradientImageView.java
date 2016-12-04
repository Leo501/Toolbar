package com.leo.sleep.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.leo.sleep.R;

/**
 * Created by leo70 on 2016/11/26.
 */

public class GradientImageView extends View {

    private Context context;
    private Rect rect;
    private GradientDrawable gradientDrawable;
    private int layout_width;
    private int layout_heigth;

    public GradientImageView(Context context) {
        this(context,null);
    }

    public GradientImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        if (attrs!=null) init(attrs);
    }

    void init(AttributeSet attrs){

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.GradientImageViewTheme);
        int startColor=array.getColor(R.styleable.GradientImageViewTheme_startColor,0xffffffff);
        int middleColor=array.getColor(R.styleable.GradientImageViewTheme_middleColor,0xaaffffff);
        int endColor=array.getColor(R.styleable.GradientImageViewTheme_endColor,0xff000000);

        gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[] { startColor,middleColor,endColor });
//         new int[]{0xff31616F,0xff15273F,0xaa000000}
        //设置成矩形
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置layout大小，用默认的就可以了
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //确定layout的大小
        layout_heigth=h;
        layout_width=w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //确定图片大小

//        rect=new Rect(0,0,layout_width,layout_heigth);
        rect=new Rect(0,0,getWidth(),getHeight());
        //设置矩形的大小
        gradientDrawable.setBounds(rect);
        //做新操作时，做下保存。与canvas.restore();是一对操作，对
        canvas.save();
        //类型设置为圆
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        //设置圆心
        gradientDrawable.setGradientCenter(0.5f,1);
        //设置半径
        gradientDrawable.setGradientRadius(Math.max(layout_heigth,layout_width));
        gradientDrawable.draw(canvas);
        canvas.restore();
    }
}
