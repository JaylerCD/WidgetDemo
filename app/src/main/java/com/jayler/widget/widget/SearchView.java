package com.jayler.widget.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JayLer on 2019/10/21.
 */
public class SearchView extends View {

    private Paint mPaint;

    private Path mPathCircle;
    private Path mPathSearch;

    private int mWidth;
    private int mHeight;

    private ValueAnimator mAnimatorStart;
    private ValueAnimator mAnimatorSearching;
    private ValueAnimator mAnimatorEnd;
    private final int DURATION = 2000;

    private float mAnimatorValue;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;
    private Handler mAnimatorHandler;
    private boolean isOver;
    private int count;

    public enum State{
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }

    private State mState = State.STARTING;

    private PathMeasure mMeasure;



    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnimator();
    }



    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(dp2px(5f));
    }

    private void initPath() {
        mMeasure = new PathMeasure();
        mPathCircle = new Path();
        mPathSearch = new Path();

        RectF oval1 = new RectF(-50, -50, 50, 50);          // 放大镜圆环
        mPathSearch.addArc(oval1, 45, 359.9f);

        RectF oval2 = new RectF(-100, -100, 100, 100);      // 外部圆环
        mPathCircle.addArc(oval2, 45, -359.9f);

        mMeasure.setPath(mPathCircle, false);
        float[] pos = new float[2];
        mMeasure.getPosTan(0, pos, null);

        mPathSearch.lineTo(pos[0], pos[1]);
    }

    private void initListener(){
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // getHandle发消息通知动画状态更新
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mState) {
                    case STARTING:
                        // 从开始动画转换好搜索动画
                        isOver = false;
                        mState = State.SEARCHING;
                        mAnimatorStart.removeAllListeners();
                        mAnimatorSearching.start();
                        break;
                    case SEARCHING:
                        if (!isOver) {  // 如果搜索未结束 则继续执行搜索动画
                            mAnimatorSearching.start();

                            count++;
                            if (count>2){       // count大于2则进入结束状态
                                isOver = true;
                            }
                        } else {        // 如果搜索已经结束 则进入结束动画
                            mState = State.ENDING;
                            mAnimatorEnd.start();
                        }
                        break;
                    case ENDING:
                        // 从结束动画转变为无状态
                        mState = State.NONE;
                        break;
                }
            }
        };
    }

    private void initAnimator() {
        mAnimatorStart = ValueAnimator.ofFloat(0, 1).setDuration(DURATION);
        mAnimatorSearching = ValueAnimator.ofFloat(0, 1).setDuration(DURATION);
        mAnimatorEnd = ValueAnimator.ofFloat(1,0).setDuration(DURATION);

        mAnimatorStart.addUpdateListener(mUpdateListener);
        mAnimatorSearching.addUpdateListener(mUpdateListener);
        mAnimatorEnd.addUpdateListener(mUpdateListener);

        mAnimatorStart.addListener(mAnimatorListener);
        mAnimatorSearching.addListener(mAnimatorListener);
        mAnimatorEnd.addListener(mAnimatorListener);

        mAnimatorStart.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.translate(mWidth/2, mHeight/2);
        canvas.drawColor(Color.parseColor("#0082D7"));

        switch (mState){
            case NONE:
                canvas.drawPath(mPathCircle, mPaint);
                canvas.drawPath(mPathSearch, mPaint);
                break;
            case STARTING:
                mMeasure.setPath(mPathSearch, false);
                Path dst = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst, true);
                canvas.drawPath(dst, mPaint);
                break;
            case SEARCHING:
                mMeasure.setPath(mPathCircle, false);
                Path dst2 = new Path();
                float stop = mMeasure.getLength() * mAnimatorValue;
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * 200f));
                mMeasure.getSegment(start, stop, dst2, true);
                canvas.drawPath(dst2, mPaint);
                break;
            case ENDING:
                mMeasure.setPath(mPathSearch, false);
                Path dst3 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst3, true);
                canvas.drawPath(dst3, mPaint);
                break;
        }

    }

    private float dp2px(float dp){
        float scale = getResources().getDisplayMetrics().density;
        return scale * dp + 0.5f;
    }
}
