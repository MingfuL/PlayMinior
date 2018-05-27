package com.example.mingfliu.waveviewplay.mini;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by MINGFLIU on 5/28/2018.
 */

public class MinionView extends View {

    private final String Tag = "[MinionView]";

    private int colorBody = Color.rgb(249, 217, 70);//衣服的颜色

    private Paint mPaint;
    private float bodyWidth;
    private float bodyHeigh;
    private static final float BODY_SCALE = 0.6f;//身体主干占整个view的比重
    private static final float BODY_WIDTH_HEIGHT_SCALE = 0.6f; //身体的比例设定为 w:h = 3:5

    private float mStrokeWidth = 4f;//描边宽度
    private RectF bodyRect;
    private float bodyRadius;

    private boolean initParamsSuccess = false;
    private boolean initPaintSuccess = false;


    private static final int DEFAULT_SIZE = 200; //View默认大小
    private int widthForUnspecified;
    private int heightForUnspecified;

    public MinionView(Context context) {
        super(context);
    }

    public MinionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MinionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        if (!initPaintSuccess) {
            Log.v(Tag, "initPaint");
            mPaint = new Paint();
            mPaint.setAntiAlias(true);

            initPaintSuccess = false;
        }
    }

    private void resetPaint(int color, Paint.Style style){
        mPaint.reset();
        mPaint.setColor(color);
        mPaint.setStyle(style);
    }

    private void initParams() {
        if (!initParamsSuccess) {
            Log.v(Tag, "initParams");
            bodyWidth = Math.min(getWidth(), getHeight()) * BODY_SCALE;
            bodyHeigh = bodyWidth / BODY_WIDTH_HEIGHT_SCALE;
            mStrokeWidth = Math.max(bodyWidth / 50, mStrokeWidth);

            //设置身体矩形的位置(居中)
            bodyRect = new RectF();
            bodyRect.left = (getWidth() - bodyWidth) / 2;
            bodyRect.top = (getHeight() - bodyHeigh) / 2;
            bodyRect.right = bodyRect.left + bodyWidth;
            bodyRect.bottom = bodyRect.top + bodyHeigh;

            bodyRadius = bodyWidth / 2;

            initParamsSuccess = true;
        }
    }

    /**
     * @param origin
     * @param isWidth 是否在测量宽
     * @return
     */
    private int measure(int origin, boolean isWidth) {
        Log.v(Tag, "measure");
        int result;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                result = specSize;
                if (isWidth) {
                    widthForUnspecified = result;
                } else {
                    heightForUnspecified = result;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                if (isWidth) {//宽或高未指定的情况下，可以由另一端推算出来 - -如果两边都没指定就用默认值
                    result = (int) (heightForUnspecified * BODY_WIDTH_HEIGHT_SCALE);
                } else {
                    result = (int) (widthForUnspecified / BODY_WIDTH_HEIGHT_SCALE);
                }
                if (result == 0) {
                    result = DEFAULT_SIZE;
                }

                break;
        }

        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v(Tag, "onMeasure");
        //自己计算尺寸的时候，直接调用 setMeasuredDimension（width, height),别用 super.onDraw()
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v(Tag, "onDraw");
        super.onDraw(canvas);
        drawBody(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.v(Tag, "onLayout");
        initParams();
        initPaint();
    }

    private void drawBody(Canvas canvas) {
        resetPaint(colorBody, Paint.Style.FILL);
        canvas.drawRoundRect(bodyRect, bodyRadius, bodyRadius, mPaint);
    }
}
