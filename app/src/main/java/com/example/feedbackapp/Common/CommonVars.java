package com.example.feedbackapp.Common;

import android.os.Environment;

/**
 * Created by Tamboli on 10/12/2015.
 */
public final class CommonVars
{

    public static final String SOLD = "S";
    public static final String UNSOLD = "U";
    public static final String HOLD = "H";
    public static final String NOT_CONFIRMED = "N";


    //Gallery Folder Name
    public final static String GALLERYIMAGES = "Gallery";

    //Gallery Folder Name
    public final static String GALLERYIMAGESTEMP = "GalleryTemp";

    //Gallery Folder Name
    public final static String VIDEOGALLERYIMAGESTEMP = "VideoGalleryTemp";

    //Camera Folder Name
    public final static String CAMERAIMAGESTEMP = "CameraTemp";
    //Camera Folder Name
    public final static String CAMERAIMAGES = "Camera";

    //parent Folder Name
    public final static String MAINFOLDERNAME = "IdeaBaazi";


    //CAMERA Downloaded File Path
    public static String DOWNLOADED_FILESPATH_CAMERA =
            Environment.getExternalStorageDirectory() + "/" +
                    MAINFOLDERNAME + "/" + CAMERAIMAGES;

    //GALLERY Downloaded File Path
    public static String DOWNLOADED_FILESPATH_GALLERY =
            Environment.getExternalStorageDirectory() + "/" +
                    MAINFOLDERNAME + "/" + GALLERYIMAGES;

    //GALLERY Downloaded File Path
    public static String DOWNLOADED_FILESPATH_GALLERY_TEMP =
            Environment.getExternalStorageDirectory() + "/" +
                    MAINFOLDERNAME + "/" + GALLERYIMAGESTEMP;

    //GALLERY Downloaded File Path
    public static String DOWNLOADED_FILESPATH_CAMERA_TEMP =
            Environment.getExternalStorageDirectory() + "/" +
                    MAINFOLDERNAME + "/" + GALLERYIMAGESTEMP;

    //GALLERY Downloaded File Path
    public static String DOWNLOADED_FILESPATH_CAMERA_VIDEO_TEMP =
            Environment.getExternalStorageDirectory() + "/" +
                    MAINFOLDERNAME + "/" + VIDEOGALLERYIMAGESTEMP;


    //All Downloaded File Path
    public static String DOWNLOADED_FILESPATH = Environment.getExternalStorageDirectory() + "/" +
            MAINFOLDERNAME + "/";


    //public static final String DATE_FORMAT_UTCZ = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_UTCZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_DDMMYY = "yyyy/MM/dd";
    public static final String DEVICE_TYPE = "android";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "LIVXIA";
    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
    public static final CharSequence DEVICE_NAME = "DIAMOND";
    public static final int ACTIVITY_TRACKER_GOAL = 1000;
    public static final int GLUCOSE_METER_MAX = 400;
    public static final String SCOPE = "read write";
    public static final String KEY = "f77230d13baaa5d1349d0a4c62bac2e6";

    public static final String Ideabaazi_CLIENT_ID = "Sph!nx";
    public static final String Ideabaazi_CLIENT_SECRET = "!de@bAAz!";
    public static final String CLIENT_ID = "Ideabaazi@app";
    public static final String CLIENT_SECRET = "SpH!nX";

    public static final String PHONE_NUMBER = "phone_number";
    public static final String CODE_RECEIVER = "otp_code_receiver";
    public static final String VERIFICATION_CODE = "verification_code";
    public static final String FROM = "from";
    public static final String SCAN_PAIR_DEVICE = "scan_pair_device";


    public static final String URL = "https://s3.ap-south-1.amazonaws.com/ideabaazi-android/ideabaazi/html/";
    public static final String HELP_CENTER_URL = URL+"help-center.html";
    public static final String HELP_URL = URL+"help.html";
    public static final String PRIVACY_URL =URL+ "privacy-policy.html";
    public static final String TERMS_SERVICE_URL = URL+"terms-service.html";
    public static final String ABOUT_US_URL = URL+"about-us.html";
    public static final String PRODUCT = "product";


}

