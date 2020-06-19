package com.example.feedbackapp.Common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Bruce Too
 * On 10/21/16.
 * At 11:25
 * Disable touch event happened in RecyclerView
 */

public class DisableRecyclerView extends RecyclerView
{

    private boolean mEnableScroll = true;

    public DisableRecyclerView(Context context) {
        super(context);
    }

    public DisableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DisableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!mEnableScroll)
            return true;
        return super.dispatchTouchEvent(ev);
    }

    public void setEnableScroll(boolean enableScroll) {
        this.mEnableScroll = enableScroll;
    }
}
