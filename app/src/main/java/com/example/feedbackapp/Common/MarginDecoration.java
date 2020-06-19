package com.example.feedbackapp.Common;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.feedbackapp.R;


public class MarginDecoration extends RecyclerView.ItemDecoration {
  private int margin;

  public MarginDecoration(Context context) {
    margin = context.getResources().getDimensionPixelSize(R.dimen.grid_spacing);
  }

  @Override
  public void getItemOffsets(
          Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.set(margin-2, margin, margin-2, margin);
  }
}