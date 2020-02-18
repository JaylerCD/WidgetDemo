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

        mReachedBarPaint = new Paint();
        mReachedBarPaint.setAntiAlias(true);
        mReachedBarPaint.setDither(true);
        mReachedBarPaint.setStyle(Paint.Style.FILL);

        mUnReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnReachedBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mUnReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 修改G币PK进度条
     *
     * @param leftCurrency
     * @param rightCurrency
     */
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
        RectF oval3 = new RectF(height / 2 + getPaddingLeft(), getPaddingTop(), width + getPaddingLeft() - height / 2, height + getPaddingTop());
        canvas.drawRect(oval3, mUnReachedBarPaint);
        RectF oval4 = new RectF(width + getPaddingLeft() - height, getPaddingTop(), width + getPaddingLeft(), height + getPaddingTop());
        canvas.drawArc(oval4, 270, 180, false, mUnReachedBarPaint);


        if (reachedWidth <= height / 2) {
            RectF oval = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
            float angle = (float) Math.toDegrees(Math.acos(((height / 2d - reachedWidth) / (height / 2d))));
            canvas.drawArc(oval, 180 - angle, 2 * angle, false, mReachedBarPaint);
        } else if (reachedWidth >= width - height / 2d) {
            RectF oval5 = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
            canvas.drawArc(oval5, 90, 180, false, mReachedBarPaint);
            RectF oval6 = new RectF((float) (height / 2d) + getPaddingLeft(), getPaddingTop(), width + getPaddingLeft() - height / 2, height + getPaddingTop());
            canvas.drawRect(oval6, mReachedBarPaint);
            float angle = (float) Math.toDegrees(Math.acos(((height / 2d - (width - reachedWidth)) / (height / 2d))));
            RectF oval7 = new RectF(width + getPaddingLeft() - height, getPaddingTop(), width + getPaddingLeft(), height + getPaddingTop());
            canvas.drawArc(oval7, 0 + angle, 360 - (2 * angle), false, mReachedBarPaint);
        } else if (reachedWidth < width - height / 2d && reachedWidth > height / 2) {
            RectF oval5 = new RectF(getPaddingLeft(), getPaddingTop(), height + getPaddingLeft(), height + getPaddingTop());
            canvas.drawArc(oval5, 90, 180, false, mReachedBarPaint);
            RectF oval6 = new RectF(height / 2 + getPaddingLeft(), getPaddingTop(), reachedWidth + getPaddingLeft(), height + getPaddingTop());
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