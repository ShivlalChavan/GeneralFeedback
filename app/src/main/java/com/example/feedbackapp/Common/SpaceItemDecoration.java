package com.example.feedbackapp.Common;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by God on 27-09-2016.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public SpaceItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public SpaceItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(5, mItemOffset, 5, mItemOffset);
    }
}
