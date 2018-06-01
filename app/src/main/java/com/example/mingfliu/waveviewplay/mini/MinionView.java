package com.example.mingfliu.waveviewplay.mini;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by MINGFLIU on 5/28/2018.
 */

public class MinionView extends View {

    private static final float BODY_SCALE = 0.6f;//身体主干占整个view的比重
    private static final float BODY_WIDTH_HEIGHT_SCALE = 0.6f; //身体的比例设定为 w:h = 3:5
    private static final int DEFAULT_SIZE = 200; //View默认大小
    private final String Tag = "[MinionView]";
    private int colorBody = Color.rgb(249, 217, 70);//身体的颜色
    private int colorClothe = Color.rgb(32, 116, 160);//衣服的颜色
    private int colorStroke = Color.BLACK;
    private Paint mPaint;
    private float bodyWidth;
    private float bodyHeigh;
    private float mStrokeWidth = 4f;//描边宽度
    private RectF bodyRect;
    private float bodyRadius;
    private boolean initParamsSuccess = false;
    private boolean initPaintSuccess = false;
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

    private void resetPaint(int color, Paint.Style style) {
        mPaint.reset();
        mPaint.setColor(color);
        mPaint.setStyle(style);
    }

    private void resetPaint(int color, Paint.Style style, float mStrokeWidth) {
        mPaint.reset();
        mPaint.setColor(color);
        mPaint.setStyle(style);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    private void initParams() {
        if (!initParamsSuccess) {
            Log.v(Tag, "initParams");
            bodyWidth = Math.min(getWidth(), getHeight()) * BODY_SCALE * BODY_WIDTH_HEIGHT_SCALE;
            bodyHeigh = Math.min(getWidth(), getHeight()) * BODY_SCALE;
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

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v(Tag, "onDraw");
        super.onDraw(canvas);
        drawBody(canvas);
        drawCloth(canvas);
        drawBodyStroke(canvas);
        drawFeet(canvas);
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

    private void drawBodyStroke(Canvas canvas) {
        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        canvas.drawRoundRect(bodyRect, bodyRadius, bodyRadius, mPaint);
    }

    private void drawCloth(Canvas canvas) {

        //衣服下半圆
        resetPaint(colorClothe, Paint.Style.FILL);
        RectF clotheRectArc = new RectF();
        clotheRectArc.top = bodyRect.bottom - 2 * bodyRadius;
        clotheRectArc.left = bodyRect.left;
        clotheRectArc.right = clotheRectArc.left + 2 * bodyRadius;
        clotheRectArc.bottom = clotheRectArc.top + 2 * bodyRadius;
        canvas.drawArc(clotheRectArc, 0, 180, true, mPaint);

        //衣服挂吊带
        resetPaint(colorClothe, Paint.Style.FILL);
        RectF clotheRect = new RectF();
        clotheRect.left = clotheRectArc.left + (clotheRectArc.right - clotheRectArc.left) / 5;
        clotheRect.right = clotheRectArc.right - (clotheRectArc.right - clotheRectArc.left) / 5;

        float widthClothRect = clotheRect.right - clotheRect.left;

        clotheRect.top = clotheRectArc.top + bodyRadius - widthClothRect * 0.5f;
        clotheRect.bottom = clotheRectArc.top + bodyRadius + mStrokeWidth;

        float heightClothRect = clotheRect.bottom - clotheRect.top;

        canvas.drawRoundRect(clotheRect, 1, 1, mPaint);

        //描边
        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        float[] points = getLines(clotheRectArc, clotheRect);
        canvas.drawLines(points, mPaint);

        //画左边的吊带
        resetPaint(colorClothe, Paint.Style.FILL);
        Path mPathLeft = new Path();
        mPathLeft.moveTo(clotheRectArc.left, clotheRectArc.top);
        mPathLeft.lineTo(clotheRect.left + widthClothRect / 5, clotheRect.top + widthClothRect / 10);
        mPathLeft.lineTo(clotheRect.left + widthClothRect / 10, clotheRect.top + widthClothRect / 5);
        double w1 = Math.sqrt(Math.pow(widthClothRect / 6, 2) * 4);
        w1 *= Math.cos(Math.PI / 4);
        mPathLeft.lineTo(clotheRectArc.left, (float) (clotheRectArc.top + w1));
        canvas.drawPath(mPathLeft, mPaint);
        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        canvas.drawPath(mPathLeft, mPaint);

        //画右边的吊带
        resetPaint(colorClothe, Paint.Style.FILL);
        Path mPathRight = new Path();
        mPathRight.moveTo(clotheRectArc.right, clotheRectArc.top);
        mPathRight.lineTo(clotheRect.right - widthClothRect / 5, clotheRect.top + widthClothRect / 10);
        mPathRight.lineTo(clotheRect.right - widthClothRect / 10, clotheRect.top + widthClothRect / 5);
        double w2 = Math.sqrt(Math.pow(widthClothRect / 6, 2) * 4);
        w2 *= Math.cos(Math.PI / 4);
        mPathRight.lineTo(clotheRectArc.right, (float) (clotheRectArc.top + w2));
        canvas.drawPath(mPathRight, mPaint);
        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        canvas.drawPath(mPathRight, mPaint);

        resetPaint(colorStroke, Paint.Style.FILL);
        canvas.drawCircle(clotheRect.left + widthClothRect / 10, clotheRect.top + widthClothRect / 10, mStrokeWidth, mPaint);
        canvas.drawCircle(clotheRect.right - widthClothRect / 10, clotheRect.top + widthClothRect / 10, mStrokeWidth, mPaint);

        //衣服口袋
        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        Path pathPocket = new Path();
        pathPocket.moveTo(clotheRect.left + widthClothRect / 4, clotheRect.top + heightClothRect / 2);
        pathPocket.lineTo(clotheRect.right - widthClothRect / 4, clotheRect.top + heightClothRect / 2);
        pathPocket.lineTo(clotheRect.right - widthClothRect / 4, clotheRect.top + heightClothRect);
        RectF rectArc = new RectF(clotheRect.left + widthClothRect / 4,
                clotheRect.top + heightClothRect / 2,
                clotheRect.right - widthClothRect / 4,
                clotheRect.top + heightClothRect * 3 / 2);
        pathPocket.addArc(rectArc, 0, 180);
        pathPocket.lineTo(clotheRect.left + widthClothRect / 4, clotheRect.top + heightClothRect / 2);
        canvas.drawPath(pathPocket, mPaint);

        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        float ww = clotheRectArc.right - clotheRectArc.left;
        RectF leftPocketRect = new RectF();
        leftPocketRect.top = clotheRectArc.top + ww / 4;
        leftPocketRect.left = clotheRectArc.left - ww / 4;
        leftPocketRect.right = clotheRectArc.left + ww / 4;
        leftPocketRect.bottom = leftPocketRect.top + ww / 2;
        Path leftPocketPath = new Path();
        leftPocketPath.addArc(leftPocketRect, 30, 45);
        canvas.drawPath(leftPocketPath, mPaint);

        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        float ww1 = clotheRectArc.right - clotheRectArc.left;
//        float hh = clotheRectArc.bottom - clotheRectArc.top;
        RectF rightPocketRect = new RectF();
        rightPocketRect.top = clotheRectArc.top + ww / 4;
        rightPocketRect.left = clotheRectArc.right - ww / 4;
        rightPocketRect.right = clotheRectArc.right + ww / 4;
        rightPocketRect.bottom = rightPocketRect.top + ww / 2;
        Path rightPocketPath = new Path();
        rightPocketPath.addArc(rightPocketRect, 150, -45);
        canvas.drawPath(rightPocketPath, mPaint);

        float www = clotheRectArc.right - clotheRectArc.left;
        resetPaint(colorStroke, Paint.Style.STROKE, mStrokeWidth);
        canvas.drawLine(clotheRectArc.left + www / 2, clotheRectArc.bottom - www / 5,
                clotheRectArc.left + www / 2, clotheRectArc.bottom, mPaint);

    }

    private float[] getLines(RectF clotheRectArc, RectF clotheRect) {
        float[] temp = new float[20];
        temp[0] = clotheRectArc.left;
        temp[1] = clotheRectArc.top + bodyRadius;
        temp[2] = clotheRect.left;
        temp[3] = temp[1];

        temp[4] = temp[2];
        temp[5] = temp[3];
        temp[6] = clotheRect.left;
        temp[7] = clotheRect.top;

        temp[8] = temp[6];
        temp[9] = temp[7];
        temp[10] = clotheRect.right;
        temp[11] = clotheRect.top;

        temp[12] = temp[10];
        temp[13] = temp[11];
        temp[14] = clotheRect.right;
        temp[15] = clotheRect.bottom - mStrokeWidth;

        temp[16] = temp[14];
        temp[17] = temp[15];
        temp[18] = clotheRectArc.right;
        temp[19] = clotheRectArc.top + bodyRadius;

        return temp;
    }

    private void drawFeet(Canvas canvas) {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(colorStroke);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        float width = bodyRect.right - bodyRect.left;
        float height = bodyRect.bottom - bodyRect.top;

        RectF leftFootRectA = new RectF(bodyRect.left + width * 0.4f - mStrokeWidth,
                bodyRect.bottom,
                bodyRect.left + width * 0.5f - mStrokeWidth,
                bodyRect.bottom + height * 0.1f);
        canvas.drawRect(leftFootRectA, mPaint);

        RectF leftFootRectB = new RectF(bodyRect.left + width * 0.2f,
                bodyRect.bottom + width * 0.05f,
                bodyRect.left + width * 0.5f - mStrokeWidth,
                bodyRect.bottom + height * 0.1f);
        canvas.drawRoundRect(leftFootRectB, width * 0.1f, width * 0.1f, mPaint);

        RectF rightFootRectA = new RectF(bodyRect.left + width * 0.5f + mStrokeWidth,
                bodyRect.bottom,
                bodyRect.left + width * 0.6f + mStrokeWidth,
                bodyRect.bottom + height * 0.1f);
        canvas.drawRect(rightFootRectA, mPaint);

        RectF rightFootRectB = new RectF(bodyRect.left + width * 0.5f + mStrokeWidth,
                bodyRect.bottom + width * 0.05f,
                bodyRect.left + width * 0.8f,
                bodyRect.bottom + height * 0.1f);
        canvas.drawRoundRect(rightFootRectB, width * 0.1f, width * 0.1f, mPaint);
    }

    //    /**
//     * @param origin
//     * @param isWidth 是否在测量宽
//     * @return
//     */
//    private int measure(int origin, boolean isWidth) {
//        Log.v(Tag, "measure");
//        int result;
//        int specMode = MeasureSpec.getMode(origin);
//        int specSize = MeasureSpec.getSize(origin);
//        switch (specMode) {
//            case MeasureSpec.EXACTLY:
//            case MeasureSpec.AT_MOST:
//                result = specSize;
//                if (isWidth) {
//                    widthForUnspecified = result;
//                } else {
//                    heightForUnspecified = result;
//                }
//                break;
//            case MeasureSpec.UNSPECIFIED:
//            default:
//                if (isWidth) {//宽或高未指定的情况下，可以由另一端推算出来 - -如果两边都没指定就用默认值
//                    result = (int) (heightForUnspecified * BODY_WIDTH_HEIGHT_SCALE);
//                } else {
//                    result = (int) (widthForUnspecified / BODY_WIDTH_HEIGHT_SCALE);
//                }
//                if (result == 0) {
//                    result = DEFAULT_SIZE;
//                }
//
//                break;
//        }
//
//        return result;
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.v(Tag, "onMeasure");
//        //自己计算尺寸的时候，直接调用 setMeasuredDimension（width, height),别用 super.onDraw()
//        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
//    }
}
