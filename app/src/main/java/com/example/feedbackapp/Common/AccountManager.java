
package com.example.feedbackapp.Common;

import android.content.Context;




public class AccountManager extends Session
{
    Session accountManager;
    private static final String loginStatusKey = "isLogin";
    private static final String loginUserStrinKey = "loginUserString";
    private static final String FIREBASETOKEN = "firebaseToken";
    private static final String OAUTHTOKEN = "oAuthToken";
    private static final String REFRESHTOKEN = "refreshToken";
    private static final String USERPROFILE = "userProfile";
    private static final String SMILEYCOUNT = "smileyCount";
    private static final String TESTRUN = "testRun";
    private static final String STEPSGOAL = "stepsGoal";
    private static final String UPLOADEDSTEPS = "tempSteps";
    private static final String CURRENTSTEPS = "currentSteps";
    private static final String DBSTEPS = "dbSteps";
    private static final String ISFIRSTTIME = "isFirstTime";
    private static final String ISNOTIFICATIONALLOW = "isNotificationAllow";
    private static final String ISADMIN = "isAdmin";


    public AccountManager(Context con)
    {
        accountManager = createSession(con, "AccountManager");
    }


  /*  public void setLoginUser(UserPOJO userInfo)
    {
        Gson gson = new Gson();
        String json = gson.toJson(userInfo); // myObject - instance of MyObject
        accountManager.putString(loginUserStrinKey, json);
    }

    public UserPOJO getLoginUser()
    {
        Gson gson = new Gson();
        String json = accountManager.getString(loginUserStrinKey, "");
        UserPOJO userInfo = gson.fromJson(json, UserPOJO.class);
        return userInfo;
    }*/

    public void setUserLogin()
    {
        accountManager.putBoolean(loginStatusKey, true);
    }

    public boolean getUserLogin()
    {
        return accountManager.getBoolean(loginStatusKey, false);
    }


    public void setUploadedSteps(int Steps)
    {
        accountManager.putInt(UPLOADEDSTEPS, Steps);

    }
    public int getUploadedSteps()
    {
        return accountManager.getInt(UPLOADEDSTEPS, 0);
    }

    public void setDBSteps(int Steps)
    {
        accountManager.putInt(DBSTEPS, Steps);

    }
    public int getDBSteps()
    {
        return accountManager.getInt(DBSTEPS, 0);
    }

    public int getCurrentSteps()
    {
        return accountManager.getInt(CURRENTSTEPS, 0);
    }

    public void setCurrentSteps(int Steps)
    {
        accountManager.putInt(CURRENTSTEPS, Steps);

    }



    public void setTestRunCompleted(boolean status)
    {
        accountManager.putBoolean(TESTRUN, status);
    }

    public boolean getTestRunCompleted()
    {
        return accountManager.getBoolean(TESTRUN, false);
    }


    public void setNotificationAllow(boolean status)
    {
        accountManager.putBoolean(ISNOTIFICATIONALLOW, status);

    }

    public boolean getNotificationAllow()
    {
        return accountManager.getBoolean(ISNOTIFICATIONALLOW, true);
    }


    public void setStepsGoal(String stepsgoal)
    {
        accountManager.putString(STEPSGOAL, stepsgoal);
    }

    public String getStepsGoal()
    {
        return accountManager.getString(STEPSGOAL, null);
    }


    public void setFirstTime(boolean state)
    {
        accountManager.putBoolean(ISFIRSTTIME, state);
    }

    public boolean getFirstTime()
    {
        return accountManager.getBoolean(ISFIRSTTIME, true);
    }


    public void logoutUser()
    {
        accountManager.putBoolean(loginStatusKey, false);
        accountManager.putString(loginUserStrinKey, null);
        accountManager.putString(OAUTHTOKEN, null);
        accountManager.putString(REFRESHTOKEN, null);
        accountManager.putString(STEPSGOAL, null);
        accountManager.putString(SMILEYCOUNT, null);
        accountManager.putBoolean(TESTRUN, false);
        accountManager.putInt(UPLOADEDSTEPS, 0);
        accountManager.putInt(CURRENTSTEPS, 0);
        accountManager.putInt(DBSTEPS, 0);



    }



    public void setAdminStatus(String adminStatus)
    {

        accountManager.putString(ISADMIN,adminStatus);
    }

    public String getAdminStatus()
    {

        return  accountManager.getString(ISADMIN,null);
    }


    public void setRefreshToken(String refreshToken)
    {
        accountManager.putString(REFRESHTOKEN, refreshToken);
    }


    public String getRefreshToken()
    {
        return accountManager.getString(REFRESHTOKEN, null);
    }

    //Smiley Count
    public void setSmileyCount(String count)
    {
        accountManager.putString(SMILEYCOUNT, count);

    }


    public String getSmileyCount()
    {
        return accountManager.getString(SMILEYCOUNT, null);
    }


    public void setOauthToken(String oauthtoken)
    {
        accountManager.putString(OAUTHTOKEN, oauthtoken);
    }


    public String getOauthToken()
    {
        return accountManager.getString(OAUTHTOKEN, null);
    }


    public void setFCMID(String fcmID)
    {
        accountManager.putString(FIREBASETOKEN, fcmID);
    }


    public String getFCMID()
    {
        return accountManager.getString(FIREBASETOKEN, null);
    }

}
