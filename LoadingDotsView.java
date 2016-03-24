package com.mario.mario4pad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

import com.mario.mario4pad.R;

import java.util.ArrayList;

/**
 * Created by Wu_youming on 2016-02-23日 11:14.
 * Everyday is another day, keep going.
 */
public class LoadingDotsView extends LinearLayoutCompat {
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
    private Handler mHandler = null;
    private Runnable mRunnable;
    private ArrayList<CircleView> images = new ArrayList<>();

    private int curIndex = 0;

    private boolean autoAnimation = true;

    public LoadingDotsView(Context context) {
        super(context);
        init(context);
    }

    public LoadingDotsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingDotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingDotsView, defStyleAttr, 0);
        count = a.getInteger(R.styleable.LoadingDotsView_dot_count, DEFAULT_COUNT);
        spacing = a.getDimensionPixelSize(R.styleable.LoadingDotsView_dot_spacing, DEFAULT_SPACING);
        radius = a.getDimensionPixelSize(R.styleable.LoadingDotsView_dot_radius, DEFAULT_RADIUS);
        default_color = a.getColor(R.styleable.LoadingDotsView_dot_default_color, getResources().getColor(DEFAULT_COLOR));
        select_color = a.getColor(R.styleable.LoadingDotsView_dot_select_color, getResources().getColor(DEFAULT_SELECT_COLOR));
        switch_duration = a.getInteger(R.styleable.LoadingDotsView_switch_duration, DEFAULT_SWITCH_DURATION);
        radius_float = a.getDimensionPixelSize(R.styleable.LoadingDotsView_dot_radius_float, DEFAULT_RADIUS_FLOAT);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        for (int i = 0; i < count; i++) {
            CircleView view = new CircleView(context);
            view.setPadding(0, 0, spacing, 0);
            images.add(view);
            this.addView(view);
        }

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                curIndex++;
                if (curIndex == images.size()) {
                    curIndex = 0;
                }
                for (int i = 0; i < images.size(); i++) {
                    if (i == curIndex)
                        images.get(i).setCurColor(select_color);
                    else
                        images.get(i).setCurColor(default_color);

//                    images.get(i).setPadding(0, 0, spacing + curIndex, 0);
                }
                if (autoAnimation)
                    mHandler.postAtTime(mRunnable, SystemClock.uptimeMillis() + switch_duration);
            }
        };
        mHandler.post(mRunnable);
    }

    /**
     * 设置是否自动切换dot的状态
     *
     * @param autoAnimation
     */
    public void setAutoAnimation(boolean autoAnimation) {
        this.autoAnimation = autoAnimation;
    }

    /**
     * 设置切换某个dot的状态
     *
     * @param curIndex
     */
    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
        mHandler.post(mRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null)
            mHandler.removeCallbacks(mRunnable);
    }

    class CircleView extends View {
        Paint mPaint = new Paint();
        int curColor = default_color;

        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(curColor);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(3);
            if (curColor == select_color)
                canvas.drawCircle(radius + radius_float, radius + radius_float, radius + radius_float, mPaint);
            else
                canvas.drawCircle(radius + radius_float, radius + radius_float, radius, mPaint);
        }

        public void setCurColor(int color) {
            curColor = color;
            postInvalidate();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            int width;
            int height;
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            } else {
                int desired = getPaddingLeft() + (radius + radius_float * 2) * 2 + getPaddingRight();
                width = desired;
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            } else {
                int desired = getPaddingTop() + (radius + radius_float * 2) * 2 + getPaddingBottom();
                height = desired;
            }
            setMeasuredDimension(width, height);

        }
    }


}
