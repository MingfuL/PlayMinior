package com.example.mingfliu.waveviewplay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mingfliu.waveviewplay.mini.MinionView;

public class MainActivity extends AppCompatActivity {

    MinionView minionView;
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LinearLayout mainView = findViewById(R.id.main);
//        mainView.addView(new MinionView(this));
        minionView = findViewById(R.id.mini_view);
        minionView.setScaleX(0);
        minionView.setScaleY(0);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(minionView, ImageView.SCALE_Y, 0.2f, 1f);
        starScaleYAnimator.setDuration(500);
        starScaleYAnimator.setStartDelay(400);
        starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(minionView, ImageView.SCALE_X, 0.2f, 1f);
        starScaleXAnimator.setDuration(500);
        starScaleXAnimator.setStartDelay(400);
        starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        animatorSet.playTogether(starScaleXAnimator,starScaleYAnimator);

        animatorSet.start();
    }
}
