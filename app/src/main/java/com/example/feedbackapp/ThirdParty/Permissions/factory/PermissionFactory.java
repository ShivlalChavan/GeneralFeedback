package com.example.feedbackapp.ThirdParty.Permissions.factory;

import android.app.Activity;

import androidx.fragment.app.Fragment;


import com.example.feedbackapp.ThirdParty.Permissions.helper.PermissionHelper;

import java.util.ArrayList;

/**
 * Created by firetrap on 28/02/2016.
 */
public class PermissionFactory {

    private Activity activity;
    private Fragment fragment;

    public PermissionFactory(Activity activity) {
        this.activity = activity;
    }

    public PermissionFactory(Fragment fragment) {
        this.fragment = fragment;
    }

    public PermissionHelper.PermissionBuilder build(ArrayList<String> permissionName) {

        if (activity != null) {

            return new PermissionHelper.PermissionBuilder(activity, permissionName);
        } else {

            return new PermissionHelper.PermissionBuilder(fragment,permissionName);
        }
    }

   /* public PermissionHelper.PermissionBuilder build(String... permissionNames) {

        if (activity != null) {

            return new PermissionHelper.PermissionBuilder(activity, permissionNames);
        } else {

            return new PermissionHelper.PermissionBuilder(fragment, permissionNames);
        }
    }*/

}