package com.jayler.widget.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by JayLer on 2019/10/23.
 */
@SuppressLint("AppCompatCustomView")
public class SlideTextView extends TextView{

    private float x;
    private float y;
    private int displayWidth;
    private int displayHeight;

    private int touchSlop;

    public SlideTextView(Context context) {
        this(context, null);
    }

    public SlideTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(2000, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       displayWidth = canvas.getWidth();
       displayHeight = canvas.getHeight();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:

                float xMove = event.getRawX();
                float distance = xMove - x;
//                if (Math.abs(distance)<touchSlop){
//                    x = xMove;
//                    return true;
//                }

                int r = (int) Math.min(displayWidth-getLeft(), 1000);
                if (getLeft()+distance<-2000+getResources().getDisplayMetrics().widthPixels) {
                    layout((int)(-2000+getResources().getDisplayMetrics().widthPixels), getTop(), 2000, getBottom());
                    x = xMove;
                    return true;
                }
                if (getLeft()+distance>0) {
                    layout(0, getTop(), 2000, getBottom());
                    x = xMove;
                    return true;
                }


                Log.e("cjl", "getLeft()+distance~: "+getLeft()+"   getRight() + distance)~："+getRight());
                layout((int)(getLeft()+distance), getTop(), (int) (getRight()+distance), getBottom());
                x = xMove;
                Log.e("cjl", "distance: "+distance);
                Log.e("cjl", "getLeft()+distance: "+getLeft()+"   getRight() + distance)："+getRight());
                break;
                default:
                    x = event.getRawX();
                    break;
        }

        return true;
    }
}
