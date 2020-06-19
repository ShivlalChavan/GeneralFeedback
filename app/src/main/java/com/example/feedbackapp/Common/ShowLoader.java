package com.example.feedbackapp.Common;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.feedbackapp.R;


public class ShowLoader
{

    private Dialog dialog;
    private Context context;

    public ShowLoader(Context context)
    {
        this.context = context;
        if (context != null)
        {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progress);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        }


    }

    public ShowLoader showDialog()
    {

        if (!((Activity) context).isFinishing())
        {
            if (!dialog.isShowing())
            {
                dialog.show();
            }
        }
        return null;
    }

    public void dismissDialog()
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
        }
    }
}
