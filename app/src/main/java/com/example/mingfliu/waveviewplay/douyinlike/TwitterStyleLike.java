package com.example.mingfliu.waveviewplay.douyinlike;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mingfliu.waveviewplay.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class TwitterStyleLike extends FrameLayout implements View.OnClickListener {

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private CircleView circleView;
    private ImageView icon;
    private int iconSize;
    private Drawable likeDrawable;
    private Drawable unLikeDrawable;
    private boolean isChecked;
    private int circleStartColor;
    private int circleEndColor;
    private Icon currentIcon;

    public TwitterStyleLike(@NonNull Context context) {
        this(context, null);
    }

    public TwitterStyleLike(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterStyleLike(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.likeview, this, true);
        icon = findViewById(R.id.icon);
        circleView = findViewById(R.id.circle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwitterStyleLike, defStyleAttr, 0);
        //设置图标size
        iconSize = typedArray.getDimensionPixelOffset(R.styleable.TwitterStyleLike_icon_size, -1);
        if (iconSize == -1) {
            iconSize = 40;
        }
        //设置点赞图标
        likeDrawable = Utils.getDrawableFromResource(getContext(), typedArray, R.styleable.TwitterStyleLike_like_drawable);
        if (likeDrawable!=null){
            setLikeDrawable(likeDrawable);
        }

        unLikeDrawable = Utils.getDrawableFromResource(getContext(), typedArray, R.styleable.TwitterStyleLike_unlike_drawable);

        if (unLikeDrawable != null) {
            setUnlikeDrawable(unLikeDrawable);
        }

        circleStartColor = typedArray.getColor(R.styleable.TwitterStyleLike_circle_start_color, 0);

        if (circleStartColor != 0) {
            circleView.setStartColor(circleStartColor);
        }

        circleEndColor = typedArray.getColor(R.styleable.TwitterStyleLike_circle_end_color, 0);

        if (circleEndColor != 0) {
            circleView.setEndColor(circleEndColor);
        }

        if (likeDrawable == null && unLikeDrawable == null) {
            setIcon();
        }
    }

    private void setIcon() {
        setLikeDrawableRes(currentIcon.getOnIconResourceId());
        setUnlikeDrawableRes(currentIcon.getOffIconResourceId());
        icon.setImageDrawable(this.unLikeDrawable);
    }

    public void setLikeDrawableRes(@DrawableRes int resId) {
        likeDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
        }

        if (isChecked) {
            icon.setImageDrawable(likeDrawable);
        }
    }

    public void setUnlikeDrawableRes(@DrawableRes int resId) {
        unLikeDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        }

        if (!isChecked) {
            icon.setImageDrawable(unLikeDrawable);
        }
    }

    private void setUnlikeDrawable(Drawable unLikeDrawable) {
        this.unLikeDrawable = unLikeDrawable;

        if (iconSize != 0) {
            this.unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        }

        if (!isChecked) {
            icon.setImageDrawable(this.unLikeDrawable);
        }
    }

    /**
     * 设置点赞之后的团
     * @param likeDrawable
     */
    private void setLikeDrawable(Drawable likeDrawable) {
        this.likeDrawable = likeDrawable;

        if (iconSize != 0) {
            this.likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
        }

        if (isChecked) {
            icon.setImageDrawable(this.likeDrawable);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
