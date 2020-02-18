package com.jayler.widget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Date;

/**
 * Created by JayLer on 2019/10/16.
 */
public class AliProgressBar extends View {

    private int mMax = 200;
    private float mCurrentProgress = 0;
    private int mReachedBarColor;
    private int mUnReachedBarColor;

    private Paint mReachedBarPaint;
    private Paint mUnReachedBarPaint;



    public AliProgressBar(Context context) {
        this(context, null);
    }

    public AliProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AliProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mReachedBarColor = Color.parseColor("#FECA21");
        mUnReachedBarColor = Color.parseColor("#FFEED3");
        Shader mShader = new RadialGradient(300,300,122, new int[]{Color.argb(100,0x00,0x00,0x00),Color.argb(0,0xff,0xff,0xff)}, new float[]{0.9f,1f}, Shader.TileMode.MIRROR);

        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);
        mReachedBarPaint.setStrokeWidth(dp2px(10));
        mReachedBarPaint.setStrokeCap(Paint.Cap.ROUND);

        mUnReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnReachedBarPaint.setColor(mUnReachedBarColor);
//        mUnReachedBarPaint.setShader(mShader);
        //mUnReachedBarPaint.setShadowLayer(5f, 0, 3, Color.parseColor("#fafafa"));
        mUnReachedBarPaint.setStrokeWidth(dp2px(10));
        mUnReachedBarPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    private float dp2px(float dp){
        float density = getResources().getDisplayMetrics().density;
        return density*dp+0.5f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth){
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        }else {
            result = isWidth ? getSuggestedMinimumWidth() - getPaddingLeft() - getPaddingRight()
                    : getSuggestedMinimumHeight() -  getPaddingTop() - getPaddingBottom();

            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        rectfDraw(canvas);

    }

    private void draw1(Canvas canvas) {
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        float reachedWidth = Math.min(mCurrentProgress*width/mMax, width);

        canvas.drawLine(getPaddingLeft(), getPaddingTop(), width+getPaddingLeft(), getPaddingTop(), mUnReachedBarPaint);
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), reachedWidth+getPaddingLeft(), getPaddingTop(), mReachedBarPaint);
        canvas.drawCircle(reachedWidth+getPaddingLeft(), getPaddingTop(), dp2px(10), mReachedBarPaint);
    }

    private void rectfDraw(Canvas canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        float reachedWidth = Math.min(mCurrentProgress*width/mMax, width);
        RectF oval2 = new RectF( getPaddingLeft(), getPaddingTop(), height+getPaddingLeft(), height+getPaddingTop());
        canvas.drawArc(oval2, 90, 180, false, mUnReachedBarPaint);
        RectF oval3 = new RectF( height/2+getPaddingLeft(), getPaddingTop(), width+getPaddingLeft()-height/2, height+getPaddingTop());
        canvas.drawRect(oval3, mUnReachedBarPaint);
        RectF oval4 = new RectF( width+getPaddingLeft()-height, getPaddingTop(), width+getPaddingLeft(), height+getPaddingTop());
        canvas.drawArc(oval4, 270, 180, false, mUnReachedBarPaint);


        if (reachedWidth <= height/2) {

            RectF oval = new RectF( getPaddingLeft(), getPaddingTop(), height+getPaddingLeft(), height+getPaddingTop());
            float angle = (float) Math.toDegrees(Math.acos(((height / 2d - reachedWidth) / (height / 2d))));
            canvas.drawArc(oval, 180-angle, 2*angle, false, mReachedBarPaint);


        }else if (reachedWidth >= width - height / 2d){

            RectF oval5 = new RectF( getPaddingLeft(), getPaddingTop(), height+getPaddingLeft(), height+getPaddingTop());
            canvas.drawArc(oval5, 90, 180, false, mReachedBarPaint);

            RectF oval6 = new RectF( (float) (height/2d)+getPaddingLeft(), getPaddingTop(), width+getPaddingLeft()-height/2, height+getPaddingTop());
            canvas.drawRect(oval6, mReachedBarPaint);

            float angle = (float) Math.toDegrees(Math.acos(((height / 2d - (width-reachedWidth)) / (height / 2d))));

            RectF oval7 = new RectF( width+getPaddingLeft()-height, getPaddingTop(), width+getPaddingLeft(), height+getPaddingTop());
            canvas.drawArc(oval7, 0+angle, 360-(2*angle), false, mReachedBarPaint);

        }else if (reachedWidth < width - height / 2d && reachedWidth > height/2){
            RectF oval5 = new RectF( getPaddingLeft(), getPaddingTop(), height+getPaddingLeft(), height+getPaddingTop());
            canvas.drawArc(oval5, 90, 180, false, mReachedBarPaint);

            RectF oval6 = new RectF( height/2+getPaddingLeft(), getPaddingTop(), reachedWidth+getPaddingLeft(), height+getPaddingTop());
            canvas.drawRect(oval6, mReachedBarPaint);

        }
    }

    public void setProgress(float progress){
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        float reachedWidth = Math.min(mCurrentProgress*width/mMax, width);
        final float p  = progress;
        ValueAnimator aggregationAnimator = ValueAnimator.ofFloat(0, progress);
        aggregationAnimator.setDuration(10000);
        aggregationAnimator.setInterpolator(new DecelerateInterpolator (1.5f));
        aggregationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Date date = new Date();
                Log.e("cjl",  date.toString() + ":  "+mCurrentProgress);
                mCurrentProgress = (float)animation.getAnimatedValue();
                if (mCurrentProgress>p) {
                    return;
                }
                invalidate();
            }
        });
        aggregationAnimator.start();
    }
}
