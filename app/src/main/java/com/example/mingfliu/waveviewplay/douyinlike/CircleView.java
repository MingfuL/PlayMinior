package com.example.mingfliu.waveviewplay.douyinlike;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;

/**
 * Created by Administrator on 2018/6/4.
 */

public class CircleView extends View {

    private int START_COLOR = 0xFFFF5722;
    private int END_COLOR = 0xFFFFC107;

    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {
                    return object.getOuterCircleRadiusProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setOuterCircleRadiusProgress(value);
                }
            };
    private static final String TAG = "CircleView";
    /**
     * 静态方法对非静态对象的属性进行操作
     */
    private static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {
                    return object.getInnerCircleRadiusProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setInnerCircleRadiusProgress(value);
                }
            };

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Paint circlePaint = new Paint();
    private Paint maskPaint = new Paint();
    private int width = 0;
    private int height = 0;
    private float outerCircleRadiusProgress = 0f;
    private float innerCircleRadiusProgress = 0f;
    private int maxCircleSize;
    private Bitmap tempBitmap;
    private Canvas tempCanvas;

    public CircleView(Context context) {
        super(context);
        init();
    }


    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        //遮挡效果，笔触是清除效果
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        maskPaint.setAntiAlias(true);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width != 0 && height != 0) {
            setMeasuredDimension(width, height);
        }
    }

    /**
     * 在onLayout之前调用,每次出现尺寸变化则初始化 最大半径,bitmap和canvas
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");
        maxCircleSize = w / 2;
        //绑定bitmap和canvas，这样，在tempCanvas做的任何操作都是在bitmap上画
        tempBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(tempBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        tempCanvas.drawColor(Color.WHITE);
        //画橙色外圈
        tempCanvas.drawCircle(getWidth() / 2, getHeight() / 2, outerCircleRadiusProgress * maxCircleSize, circlePaint);
        //画遮挡层
        tempCanvas.drawCircle(getWidth() / 2, getHeight() / 2, innerCircleRadiusProgress * maxCircleSize + 1, maskPaint);
        //投射到View的画布中
        canvas.drawBitmap(tempBitmap, 0, 0, null);
    }

    public float getOuterCircleRadiusProgress() {
        return outerCircleRadiusProgress;
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        updateCircleColor();
        postInvalidate();
    }

    public float getInnerCircleRadiusProgress() {
        return innerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        postInvalidate();
    }

    private void updateCircleColor() {
        float colorProgress = (float) Utils.clamp(outerCircleRadiusProgress, 0.5, 1);
        colorProgress = (float) Utils.mapValueFromRangeToRange(colorProgress, 0.5f, 1f, 0f, 1f);
        this.circlePaint.setColor((Integer) argbEvaluator.evaluate(colorProgress, START_COLOR, END_COLOR));
    }

    public void setStartColor(@ColorInt int color) {
        START_COLOR = color;
        invalidate();
    }

    public void setEndColor(@ColorInt int color) {
        END_COLOR = color;
        invalidate();
    }
}
