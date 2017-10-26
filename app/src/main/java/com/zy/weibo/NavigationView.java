package com.zy.weibo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zy
 * Date：2017/10/26
 * Time：上午10:52
 */
public class NavigationView extends View {


    public NavigationView(Context context) {
        super(context);
        initPaint(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    private Paint mPaint;

    /**
     * 分段颜色
     */
    private static final int[] SECTION_COLORS = new int[2];

    /**
     * 进度条当前值
     */
    private float currentCount = 0.3f;

    private int pageSelected = 0;

    private void initPaint(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.FILL);//设置填充样式
        mPaint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setFilterBitmap(true);//加快显示速度，本设置项依赖于dither和xfermode的设置
        SECTION_COLORS[0] = ContextCompat.getColor(context, R.color.progress_start);
        SECTION_COLORS[1] = ContextCompat.getColor(context, R.color.progress_end);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = getWidth();
        int mHeight = getHeight();
        int layerId = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        //绘制两端是半圆的矩形
        RectF rectBlackBg;
        if (currentCount > 1) {
            rectBlackBg = new RectF(mWidth * (currentCount - 1), 0, mWidth, mHeight);
        } else {
            rectBlackBg = new RectF(0, 0, mWidth * currentCount, mHeight);
        }
        canvas.drawRoundRect(rectBlackBg, mHeight / 2, mHeight / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //绘制渐变色
        int count = 2;
        int[] colors = new int[count];
        System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
        LinearGradient shader = new LinearGradient(0, mHeight / 2, mWidth, mHeight / 2, colors, null, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
        RectF rectProgressBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectProgressBg, 0, 0, mPaint);
        //最后将画笔去除Xfermode
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    public void setCurrentCount(float currentCount) {
        if (currentCount == 0) {
            //viewpager翻页成功，留在最右边
            if (pageSelected != 0)
                currentCount = 1;
        }
        //进度换算
        currentCount = 2 * currentCount * 0.7f + 0.3f;
        this.currentCount = currentCount;
        invalidate();
    }

    public void setPageSelected(int pageSelected) {
        this.pageSelected = pageSelected;
    }

}
