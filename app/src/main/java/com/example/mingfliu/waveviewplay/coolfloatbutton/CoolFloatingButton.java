package com.example.mingfliu.waveviewplay.coolfloatbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mingfliu.waveviewplay.R;

/**
 * Created by Administrator on 2018/6/6.
 */

public class CoolFloatingButton extends RelativeLayout implements View.OnClickListener {
    /**
     * 默认展开在按钮的上方
     */
    private int orientation;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 默认按钮的tag标识
     */
    private Object defaultTag = 0;

    public CoolFloatingButton(Context context) {
        super(context);
    }

    public CoolFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(context, attrs);
        init(context);
    }

    public CoolFloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttrs(context, attrs);
        init(context);
    }

    public CoolFloatingButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadAttrs(context, attrs);
        init(context);
    }

    private void loadAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CoolFloatingButton);
        orientation = typedArray.getInt(R.styleable.CoolFloatingButton_expand_orientation, ExpandOrientation.TOP.ordinal());
        typedArray.recycle();
    }

    private void init(Context context) {
        this.context = context;
        FabAttributes fabAttributes = new FabAttributes.Builder()
                .setSrc(ContextCompat.getDrawable(context, R.drawable.add))
                .setElevation(20)
                .setTag(defaultTag)
                .build();

        FloatingActionButton fab = new FloatingActionButton(context);
        fab.setId(R.id.default_fab_id);
        fab.setOnClickListener(this);

        setAttributeToButton(fab, fabAttributes);
    }

    private void setAttributeToButton(FloatingActionButton fab, FabAttributes fabAttributes) {
        fab.setTag(fabAttributes.getBuilder().getTag());
        fab.setSize(fabAttributes.getBuilder().getFabSize());
        fab.setImageDrawable(fabAttributes.getBuilder().getSrc());
        fab.setRippleColor(fabAttributes.getBuilder().getRippleColor());
        fab.setBackgroundTintList(ColorStateList.valueOf(fabAttributes.getBuilder().getBackgroundTint()));

    }

    @Override
    public void onClick(View v) {

    }
}
