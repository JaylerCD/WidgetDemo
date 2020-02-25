package com.jayler.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.jayler.widget.DensityUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 跨直播间PK进度条
 */
public class LivePublishLinkProgress extends View {

    private float mLeftCurrency, mRightCurrency;
    private Paint mTextPaint;
    private Paint mReachedBarPaint;
    private Paint mUnReachedBarPaint;

    public LivePublishLinkProgress(@NonNull Context context) {
        this(context, null);
    }

    public LivePublishLinkProgress(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(DensityUtils.sp2px(getContext(), 11));
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void updateCurrency(int leftCurrency, int rightCurrency) {
        this.mLeftCurrency = leftCurrency;
        this.mRightCurrency = rightCurrency;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectfDraw(canvas);
    }


    private void rectfDraw(Canvas canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float radius = (float) Math.floor(height / 2d); // fix float四舍五入精度丢失问题

        LinearGradient reachedBarGradient = new LinearGradient(width, height, 0, 0,
                new int[]{Color.parseColor("#FF4B88"), Color.parseColor("#EB0057")}, null,
                Shader.TileMode.CLAMP);
        LinearGradient unReachedBarGradient = new LinearGradient(0, 0, width, height,
                new int[]{Color.parseColor("#00D8FF"), Color.parseColor("#003EFF")}, null,
                Shader.TileMode.CLAMP);
        mReachedBarPaint.setShader(reachedBarGradient);
        mUnReachedBarPaint.setShader(unReachedBarGradient);

        float reachedWidth;
        if (mLeftCurrency - mRightCurrency == 0) {
            reachedWidth = width / 2;
        } else {
            reachedWidth = mLeftCurrency / (mLeftCurrency + mRightCurrency) * width;
        }

        RectF oval2 = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
        canvas.drawArc(oval2, 90, 180, false, mUnReachedBarPaint);
        RectF oval3 = new RectF(radius + getPaddingLeft(), getPaddingTop(), width + getPaddingLeft() - radius, height + getPaddingTop());
        canvas.drawRect(oval3, mUnReachedBarPaint);
        RectF oval4 = new RectF(width + getPaddingLeft() - height, getPaddingTop(), width + getPaddingLeft(), height + getPaddingTop());
        canvas.drawArc(oval4, 270, 180, false, mUnReachedBarPaint);

        if (reachedWidth <= radius) {
            RectF oval = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
            float angle = (float) Math.toDegrees(Math.acos(((radius - reachedWidth) / radius)));
            canvas.drawArc(oval, 180 - angle, 2 * angle, false, mReachedBarPaint);
        } else if (reachedWidth >= width - radius) {
            RectF oval5 = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
            canvas.drawArc(oval5, 90, 180, false, mReachedBarPaint);
            RectF oval6 = new RectF(radius + getPaddingLeft(), getPaddingTop(), width + getPaddingLeft() - radius, height + getPaddingTop());
            canvas.drawRect(oval6, mReachedBarPaint);
            float angle = (float) Math.toDegrees(Math.acos(((radius - (width - reachedWidth)) / radius)));
            RectF oval7 = new RectF(width + getPaddingLeft() - height, getPaddingTop(), width + getPaddingLeft(), height + getPaddingTop());
            canvas.drawArc(oval7, angle, 360 - (2 * angle), false, mReachedBarPaint);
        } else if (reachedWidth < width - radius && reachedWidth > radius) {
            RectF oval5 = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
            canvas.drawArc(oval5, 90, 180, false, mReachedBarPaint);
            RectF oval6 = new RectF(radius + getPaddingLeft(), getPaddingTop(), reachedWidth + getPaddingLeft(), height + getPaddingTop());
            canvas.drawRect(oval6, mReachedBarPaint);
        }

        //绘制文字
        RectF mBackGroundRect = new RectF(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + width, getPaddingTop() + height);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        float baseline = mBackGroundRect.top + (mBackGroundRect.bottom - mBackGroundRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        float textWidth = mTextPaint.measureText(String.format("对方 %1dG币", (int) mRightCurrency));
        canvas.drawText(String.format("我方 %1dG币", (int) mLeftCurrency), getPaddingLeft() + DensityUtils.dp2px(getContext(), 15), baseline, mTextPaint);
        canvas.drawText(String.format("对方 %1dG币", (int) mRightCurrency), getPaddingLeft() + width - DensityUtils.dp2px(getContext(), 15) - textWidth, baseline, mTextPaint);
    }
}