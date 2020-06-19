package com.example.feedbackapp.Common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDexApplication;


public class AppController extends MultiDexApplication
{

    public static final String TAG = AppController.class.getSimpleName();
    private static Context context;

    private static AppController mInstance;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        try
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static Context getAppContext()
    {
        return AppController.context;
    }


}