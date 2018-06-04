package com.example.mingfliu.waveviewplay.douyinlike;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2018/6/4.
 */

public class Utils {

    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    /**
     * value 低于low的返回low,否则返回 value，大于high时返回height
     * @param value
     * @param low
     * @param high
     * @return
     */
    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static BitmapDrawable resizeDrawable(Context context, Drawable drawable, int width, int height) {

        Bitmap bitmap = getBitmap(drawable, width, height);
        //获取bitmap修改尺寸，不会对bitmap有裁剪，其中，true是平滑法处理
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
    }

    public static Bitmap getBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat) {
            return getBitmap((VectorDrawableCompat) drawable, width, height);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable, width, height);
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

    public static Drawable getDrawableFromResource(Context context, TypedArray typedArray, int styleableIndexId) {
        int resourceId = typedArray.getResourceId(styleableIndexId, -1);
        return (-1 != resourceId) ? ContextCompat.getDrawable(context, resourceId) : null;
    }
}
