package com.example.feedbackapp.Interface;

/**
 * Created by sunil on 27-Feb-16.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}

