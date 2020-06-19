package com.example.feedbackapp.Common;

import android.app.Activity;
import android.util.Log;


import com.example.feedbackapp.ThirdParty.Permissions.action.OnDenyAction;
import com.example.feedbackapp.ThirdParty.Permissions.action.OnGrantAction;
import com.example.feedbackapp.ThirdParty.Permissions.helper.PermissionHelper;

import java.util.ArrayList;


/**
 * Created by DELL on 04/02/2017.
 */

public class AppPermissionForActivity
{

   private PermissionHelper.PermissionBuilder permissionRequest;
   private int requestCode;
   private Activity context;
   private OnPermissionRequestCompleted caller;
   private ArrayList<String> permissions;
   private boolean allTrue;
   private boolean isRational;
   private int viewID;

   public PermissionHelper.PermissionBuilder AskForPermission(Activity context,
                                                              OnPermissionRequestCompleted caller,
                                                              int requestCode,
                                                              ArrayList<String> permissions, int viewID)
   {
      this.requestCode = requestCode;
      this.caller = caller;
      this.context = context;
      this.permissions = permissions;
      this.viewID=viewID;
      return getPermissions(context, requestCode, permissions,viewID);
   }

   // Interface to be implemented by calling activity
   public interface OnPermissionRequestCompleted
   {
      public void permissionResponse(boolean allTrue, boolean isRational, int viewID);
   }

   private PermissionHelper.PermissionBuilder getPermissions(Activity context, int requestCode,
                                                             ArrayList<String> permissions, int viewID)
   {
      permissionRequest = PermissionHelper.with(context)
                                          .build(permissions)
                                          .onPermissionsDenied(onDenyAction)
                                          .onPermissionsGranted(onGrantAction)
                                          .request(requestCode);
      return permissionRequest;
   }

   private OnDenyAction onDenyAction = new OnDenyAction()
   {
      @Override
      public void call(int requestCode, boolean shouldShowRequestPermissionRationale)
      {

         // Should we show an explanation?
         if (shouldShowRequestPermissionRationale)
         {
            Log.e("Status", "kay aala");
            allTrue = false;
            isRational = true;
            caller.permissionResponse(allTrue, isRational,viewID);
         }
         else
         {
            Log.e("Status", "nahi aala");
            allTrue = false;
            isRational = false;
            caller.permissionResponse(allTrue, isRational,viewID);
         }
      }
   };

   private OnGrantAction onGrantAction = new OnGrantAction()
   {
      @Override
      public void call(int localRequestCode)
      {
         if (requestCode == localRequestCode)
         {
            Log.e("Status", "Success aala");
            allTrue = true;
            isRational = false;
            caller.permissionResponse(allTrue, isRational,viewID);
         }
      }
   };


}

