package com.example.mingfliu.waveviewplay.coolfloatbutton;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by Administrator on 2018/6/6.
 */

public class FabAttributes {

    private FabAttributes.Builder builder;

    private FabAttributes(FabAttributes.Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {

        //fab插入的图片
        private Drawable src;
        //fab的背景色
        private int backgroundTint = Color.parseColor("#FF4081");
        //fab的阴影大小
        private int elevation = 0;
        //fab的大小
        private int fabSize = FloatingActionButton.SIZE_NORMAL;
        //fab按下的颜色
        private int rippleColor = Color.parseColor("#FF4081");
        //fab标记
        private Object tag;

        public Builder() {
        }

        public Drawable getSrc() {
            return src;
        }

        public int getBackgroundTint() {
            return backgroundTint;
        }

        public int getElevation() {
            return elevation;
        }

        public int getFabSize() {
            return fabSize;
        }

        public int getRippleColor() {
            return rippleColor;
        }

        public Object getTag() {
            return tag;
        }

        public Builder setSrc(Drawable src) {
            this.src = src;
            return this;
        }

        public Builder setBackgroundTint(int backgroundTint) {
            this.backgroundTint = backgroundTint;
            return this;
        }

        public Builder setElevation(int elevation) {
            this.elevation = elevation;
            return this;
        }

        public Builder setFabSize(int fabSize) {
            this.fabSize = fabSize;
            return this;
        }

        public Builder setRippleColor(int rippleColor) {
            this.rippleColor = rippleColor;
            return this;
        }

        public Builder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public FabAttributes build() {
            return new FabAttributes(this);
        }
    }
}
