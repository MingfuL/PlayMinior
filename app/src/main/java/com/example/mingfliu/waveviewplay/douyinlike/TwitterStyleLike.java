package com.example.mingfliu.waveviewplay.douyinlike;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mingfliu.waveviewplay.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class TwitterStyleLike extends FrameLayout implements View.OnClickListener {

    private final static String TAG = "TwitterStyleLike";

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
//    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(2);

    private CircleView circleView;
    private TextView textView;
    private int circleStartColor;
    private int circleEndColor;
    private AnimatorSet animatorSet;

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
        Log.d(TAG, "TwitterLike init-->begin inflate");
        //inflate的时候，仅仅只是初始化里面的view，调用构造器，没有测量，没有布局，没有draw
        LayoutInflater.from(context).inflate(R.layout.likeview, this, true);
        textView = findViewById(R.id.icon);
        Log.d(TAG, "init-->findViewById before");
        circleView = findViewById(R.id.circle);
        Log.d(TAG, "init-->findViewById after");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwitterStyleLike, defStyleAttr, 0);
        //设置图标size
        circleStartColor = typedArray.getColor(R.styleable.TwitterStyleLike_circle_start_color, 0xFFFF5722);
        circleEndColor = typedArray.getColor(R.styleable.TwitterStyleLike_circle_end_color, 0xFFFFC107);

        circleView.setStartColor(circleStartColor);
        circleView.setEndColor(circleEndColor);

        Log.d(TAG, "TwitterStyleLike after init");
    }

    @Override
    public void onClick(View v) {

    }

    public void setContent(String s){
        textView.setText(s);
    }

    public void startAnimate(){
        textView.animate().cancel();
        textView.setScaleX(0);
        textView.setScaleY(0);
        circleView.setInnerCircleRadiusProgress(0);
        circleView.setOuterCircleRadiusProgress(0);

        animatorSet = new AnimatorSet();

        ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        outerCircleAnimator.setDuration(550);
        outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        innerCircleAnimator.setDuration(500);
        innerCircleAnimator.setStartDelay(200);
        innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(textView, ImageView.SCALE_Y, 0.2f, 1f);
        starScaleYAnimator.setDuration(550);
        starScaleYAnimator.setStartDelay(550);
        starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(textView, ImageView.SCALE_X, 0.2f, 1f);
        starScaleXAnimator.setDuration(550);
        starScaleXAnimator.setStartDelay(550);
        starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        animatorSet.playTogether(
                outerCircleAnimator,
                innerCircleAnimator,
                starScaleYAnimator,
                starScaleXAnimator
        );

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                circleView.setInnerCircleRadiusProgress(0);
                circleView.setOuterCircleRadiusProgress(0);
                textView.setScaleX(1);
                textView.setScaleY(1);
            }

            @Override public void onAnimationEnd(Animator animation) {

            }
        });

        animatorSet.start();
    }
}
