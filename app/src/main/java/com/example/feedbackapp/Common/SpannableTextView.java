package com.example.feedbackapp.Common;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 * Created by God on 19-08-2017.
 */

public class SpannableTextView extends ClickableSpan
{

    private boolean isUnderline = true;

    /**
     * Constructor
     */
    public SpannableTextView(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#1b76d3"));
    }

    @Override
    public void onClick(View widget) {

        Log.e("Click","Click");
    }
}