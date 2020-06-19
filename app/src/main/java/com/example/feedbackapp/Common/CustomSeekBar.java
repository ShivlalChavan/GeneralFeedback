package com.example.feedbackapp.Common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by admin on 5/12/2016.
 */
public class CustomSeekBar extends SeekBar
{

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
