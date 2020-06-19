package com.example.feedbackapp.Common;


import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;

import com.example.feedbackapp.R;
import com.example.feedbackapp.ThirdParty.CircularProgress.CircleProgressView;


public class ShowProgressLoader
{

    private Dialog dialog;
    private Context context;
    private CircleProgressView mCircleView;

    public ShowProgressLoader(Context context)
    {
        this.context = context;
    }

    public ShowProgressLoader showDialog()
    {

        if (dialog != null)
        {
            dialog = null;
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_with_status);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        mCircleView = (CircleProgressView) dialog.findViewById(R.id.circleView);
        mCircleView.setMaxValue(100);
        mCircleView.setValue(0);

        dialog.show();
        return null;
    }

    public void dismissDialog()
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
            Log.e("Value in Method", "" + mCircleView.getCurrentValue());
        }
    }




    public void updateDialog(int updateValue)
    {
        mCircleView.setValueAnimated(updateValue, 1500);
    }
}
