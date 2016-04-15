package com.mario.learningtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mario.learningtest.R;

/**
 * Created by Wu_youming on 2016-04-14日 18:00.
 * Everyday is another day, keep going.
 */
public class LoadingDots extends View {
    private int count = 0;
    private static final int DEFAULT_COUNT = 3;
    private int spacing = 0;
    private static final int DEFAULT_SPACING = 4;
    private int radius = 0;
    private static final int DEFAULT_RADIUS = 4;
    private int default_color = 0x0;
    private static final int DEFAULT_COLOR = R.color.colorPrimary;
    private int select_color = 0x0;
    private static final int DEFAULT_SELECT_COLOR = R.color.colorAccent;

    private int switch_duration = 0;
    private static final int DEFAULT_SWITCH_DURATION = 300;


    private int radius_float = 0;
    private static final int DEFAULT_RADIUS_FLOAT = 4;


    private int currentIndex = 0;
    private Paint mPaint;
    private boolean autoAnimation = true;

    public LoadingDots(Context context) {
        super(context);
    }

    public LoadingDots(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingDots(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingDots, defStyleAttr, 0);
        count = a.getInteger(R.styleable.LoadingDots_dot_count, DEFAULT_COUNT);
        spacing = a.getDimensionPixelSize(R.styleable.LoadingDots_dot_spacing, DEFAULT_SPACING);
        radius = a.getDimensionPixelSize(R.styleable.LoadingDots_dot_radius, DEFAULT_RADIUS);
        default_color = a.getColor(R.styleable.LoadingDots_dot_default_color, getResources().getColor(DEFAULT_COLOR));
        select_color = a.getColor(R.styleable.LoadingDots_dot_select_color, getResources().getColor(DEFAULT_SELECT_COLOR));
        switch_duration = a.getInteger(R.styleable.LoadingDots_switch_duration, DEFAULT_SWITCH_DURATION);
        radius_float = a.getDimensionPixelSize(R.styleable.LoadingDots_dot_radius_float, DEFAULT_RADIUS_FLOAT);
        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        if (currentIndex == count)
            currentIndex = 0;
        for (int i = 0; i < count; i++) {
            if (currentIndex == i)
                mPaint.setColor(select_color);
            else
                mPaint.setColor(default_color);
            if (i < currentIndex)
                canvas.drawCircle(spacing * (i + 1) + radius * (2 * i + 1), getHeight() / 2, radius, mPaint);
            else if (i == currentIndex) {
                canvas.drawCircle(spacing * (i + 1) + radius * (2 * i + 1) + radius_float, getHeight() / 2, radius + radius_float, mPaint);
            } else
                canvas.drawCircle(spacing * (i + 1) + radius * (2 * i + 1) + radius_float * 2, getHeight() / 2, radius, mPaint);
        }
        currentIndex++;
        if (autoAnimation)
            postInvalidateDelayed(switch_duration);
    }

    /**
     * 设置切换某个dot的状态
     *
     * @param curIndex
     */
    public void setCurIndex(int curIndex) {
        this.currentIndex = curIndex;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = spacing * (count + 1) + radius * 2 * count + radius_float * 2;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (radius + radius_float) * 2;
        }
        return result;
    }
}
