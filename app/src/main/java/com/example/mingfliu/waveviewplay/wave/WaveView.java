package com.example.mingfliu.waveviewplay.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MINGFLIU on 5/27/2018.
 */

public class WaveView extends View {

    private Path mAbovePath, mBelowWavePath;
    private Paint mAboveWavePaint, mBelowWavePaint;
    private DrawFilter mDrawFilter;
    private float angelOffset;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        //初始化路径
        mAbovePath = new Path();
        mBelowWavePath = new Path();

        //初始化画笔
        mAboveWavePaint = new Paint();
        mAboveWavePaint.setAntiAlias(true);
        mAboveWavePaint.setStyle(Paint.Style.FILL);
        mAboveWavePaint.setColor(Color.WHITE);

        mBelowWavePaint = new Paint();
        mBelowWavePaint.setAntiAlias(true);
        mBelowWavePaint.setStyle(Paint.Style.FILL);
        mBelowWavePaint.setColor(Color.WHITE);
        mBelowWavePaint.setAlpha(80);

        //画布过滤器，设置整个画布抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0,
                Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(mDrawFilter);

        mAbovePath.reset();
        mBelowWavePath.reset();

        angelOffset = -0.1f;
        float y1, y2;
        double angel = 2*Math.PI/getWidth();

        mAbovePath.moveTo(getLeft(), getBottom());
        mBelowWavePath.moveTo(getLeft(),getBottom());
    }
}
