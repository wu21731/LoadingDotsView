package com.wym.gank.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wym.gank.R;

/**
 * Created by Ramo on 2017/3/25.
 * Everyday is another day, keep going.
 */

public class DynamicLoadingView extends View {

    private P p = new P();
    private Paint mPaint;
    private Runnable runnable;

    public DynamicLoadingView(Context context) {
        this(context, null);
    }

    public DynamicLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DynamicLoadingView, defStyleAttr, 0);
        p.count = array.getInt(R.styleable.DynamicLoadingView_count_number, p.count);
        p.space = array.getDimensionPixelSize(R.styleable.DynamicLoadingView_item_spacing, p.space);
        p.radius = array.getDimensionPixelSize(R.styleable.DynamicLoadingView_item_radius, p.radius);
        p.floatRadius = array.getDimensionPixelSize(R.styleable.DynamicLoadingView_item_float_radius, p.floatRadius);
        p.defaultColor = array.getColor(R.styleable.DynamicLoadingView_item_default_color, p.defaultColor);
        p.changeColor = array.getColor(R.styleable.DynamicLoadingView_item_change_color, p.changeColor);
        p.autoPlay = array.getBoolean(R.styleable.DynamicLoadingView_item_auto_play, p.autoPlay);
        p.delay = array.getInt(R.styleable.DynamicLoadingView_item_play_delay, p.delay);
        array.recycle();
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        runnable = new Runnable() {
            @Override
            public void run() {
                p.current++;
                if (p.current == p.count) {
                    p.current = 0;
                }
                invalidate();
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getPaddingStart(), p.radius + p.floatRadius);
        int x;
        for (int i = 0; i < p.count; i++) {
            x = (i == 0 ? p.radius : p.space + p.radius * 2);
            if (i == p.current) {
                mPaint.setColor(p.changeColor);
                x += p.floatRadius;
                canvas.translate(x, 0);
                canvas.drawCircle(0, 0, p.radius + p.floatRadius, mPaint);
                canvas.translate(p.floatRadius, 0);
            } else {
                mPaint.setColor(p.defaultColor);
                canvas.translate(x, 0);
                canvas.drawCircle(0, 0, p.radius, mPaint);
            }
        }
        if (p.autoPlay) {
            removeCallbacks(runnable); //避免onDraw极短时间内被多次执行的时候，导致执行异常
            postDelayed(runnable, p.delay);
        }

    }

    public void setCount(int count) {
        p.count = count;
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        invalidate();
    }

    public int getCurrentIndex() {
        return p.current;
    }

    public void setCurrentIndex(int index) {
        if (index < 0 || index > p.count) {
            throw new IndexOutOfBoundsException("index must be >=0 and < count");
        }
        p.current = index;
        invalidate();
    }

    public void setAuto(boolean auto) {
        this.p.autoPlay = auto;
        invalidate();
    }

    int widthMeasureSpec;
    int heightMeasureSpec;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        return specMode == MeasureSpec.EXACTLY ? specSize : getPaddingStart() + getPaddingEnd() + p.radius * 2 * p.count + p.space * (p.count - 1) + p.floatRadius * 2;

    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        return specMode == MeasureSpec.EXACTLY ? specSize : getPaddingTop() + getPaddingBottom() + (p.radius + p.floatRadius) * 2;
    }

    private class P {
        private int count = 3;
        private int space = 4;
        private int radius = 4;
        private int defaultColor = Color.BLUE;
        private int changeColor = Color.RED;
        private int current = 0;
        private boolean autoPlay = true;
        private int delay = 500;
        private int floatRadius = 2;

    }


}
