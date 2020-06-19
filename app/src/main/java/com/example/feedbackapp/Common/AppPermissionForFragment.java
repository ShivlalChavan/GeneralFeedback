package com.example.feedbackapp.Common;


import android.util.Log;

import androidx.fragment.app.Fragment;


import com.example.feedbackapp.ThirdParty.Permissions.action.OnDenyAction;
import com.example.feedbackapp.ThirdParty.Permissions.action.OnGrantAction;
import com.example.feedbackapp.ThirdParty.Permissions.helper.PermissionHelper;

import java.util.ArrayList;

/**
 * Created by Admin on 10/03/2017.
 */

public class AppPermissionForFragment
{
   private PermissionHelper.PermissionBuilder permissionRequest;
   private int requestCode;
   private Fragment context;
   private OnPermissionRequestCompleted caller;
   private ArrayList<String> permissions;
   private boolean allTrue;
   private boolean isRational;
   private int viewID;

   public PermissionHelper.PermissionBuilder AskForPermission(Fragment context,
                                                              OnPermissionRequestCompleted caller,
                                                              int requestCode,
                                                              ArrayList<String> permissions, int viewID)
   {
      this.requestCode = requestCode;
      this.caller = caller;
      this.context = context;
      this.viewID=viewID;
      this.permissions = permissions;

      return getPermissions(context, requestCode, permissions,viewID);
   }

   // Interface to be implemented by calling activity
   public interface OnPermissionRequestCompleted
   {
      public void permissionResponse(boolean allTrue, boolean isRational, int viewID);
   }

   private PermissionHelper.PermissionBuilder getPermissions(Fragment context, int requestCode,
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