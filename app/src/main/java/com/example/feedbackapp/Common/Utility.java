package com.example.feedbackapp.Common;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.example.feedbackapp.Interface.AlertInterface;
import com.example.feedbackapp.Interface.DateSelected;
import com.example.feedbackapp.Interface.OnClicked;
import com.example.feedbackapp.Interface.TimeSelected;

import com.example.feedbackapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by DELL on 17/12/2016.
 */

public class Utility
{
    private static boolean grantFlagForPermission = false;
    private static boolean grantFlagForPermissionWarning = false;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static Uri fileUri; // file url to store image/video
    public static String mCurrentPhotoPath;

    private static int mMaxLength = 60;
    private static int mQuality = 0;
    private static long mMaxSize = 10; //In Mb
    private static final long K = 1024;
    private boolean mCompress = false;

    public static final int[] MATERIAL_COLORS = {
            rgb("#23B9A4"), rgb("#f1c40f"), rgb("#F94E7F"), rgb("#3498db"),
            rgb("#1f74a4"), rgb("#a41f74"), rgb("#83b923"), rgb("#1f74a4"),
            rgb("#eb97cd"),
            rgb("#acd7ef")
    };

    public static String abbreviateString(String input, int maxLength)
    {
        if (input.length() <= maxLength)
        {
            return input;
        }
        else
        {
            return input.substring(0, maxLength - 2) + "..";
        }
    }

    public static int rgb(String hex)
    {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }

    private int getHoursValue(int hours)
    {
        return hours - 12;
    }

    public static void hideSoftKeyboard(Context context)
    {
        if (context instanceof AppCompatActivity)
        {
            if (((AppCompatActivity) context).getCurrentFocus() != null)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) ((AppCompatActivity) context)
                        .getSystemService(((AppCompatActivity) context).INPUT_METHOD_SERVICE);
                inputMethodManager
                        .hideSoftInputFromWindow(((AppCompatActivity) context).getCurrentFocus()
                                .getWindowToken(), 0);
            }
        }
        else if (context instanceof Activity)
        {
            if (((Activity) context).getCurrentFocus() != null)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) ((Activity) context)
                        .getSystemService(((Activity) context).INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }



    public static void showDefaulutDatePicker(final Context context, final DateSelected dateSelected)
    {
        final Calendar myCalendar = Calendar.getInstance();

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-mm-dd hh:mm:ss"; //In which you need put here

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                dateSelected.onDateClicked((year)+"-"+(monthOfYear+1)+"-"+(dayOfMonth));
            }

        };

        DatePickerDialog dialog = new DatePickerDialog(context, dateListener, year, month, day);


       // dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());

       /* AccountManager accountManager = new AccountManager(context);
          dialog.getDatePicker()
                .setMinDate();
         dialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());*/

        //  dialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());

        dialog.setTitle("");

        dialog.show();

    }


    public static void showDatePicker(final Context context, final DateSelected dateSelected)
    {
        final Calendar myCalendar = Calendar.getInstance();

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-mm-dd hh:mm:ss"; //In which you need put here

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                dateSelected.onDateClicked((year)+"-"+(monthOfYear+1)+"-"+(dayOfMonth));
            }

        };

        DatePickerDialog dialog = new DatePickerDialog(context, dateListener, year, month, day);


        dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());

       /* AccountManager accountManager = new AccountManager(context);
          dialog.getDatePicker()
                .setMinDate();
         dialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());*/

      //  dialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());

        dialog.setTitle("");

        dialog.show();

    }



    public static String compareDates(String d1, String d2)
    {
        String dateCompareFlag="";
        try{



            //1
            // Create 2 dates starts
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(d1);

            Date date2 = sdf.parse(d2);


            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if(date1.after(date2)){

                Log.e("Date1 is","after Date2");

                dateCompareFlag = "isAfter";

                return dateCompareFlag;

            }
            // before() will return true if and only if date1 is before date2
            if(date1.before(date2)){

                Log.e("Date1 is","before Date2");

                dateCompareFlag = "isBefore";
                return dateCompareFlag;

            }

            //equals() returns true if both the dates are equal
            if(date1.equals(date2)){

                Log.e("Date1 is","equal Date2");
                dateCompareFlag = "isEqual";

                return dateCompareFlag;

            }

        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
        return dateCompareFlag;
    }



    public static void showTimePicker(final Context context , final TimeSelected timeSelected){


        final Calendar myCalendar = Calendar.getInstance();

        int hourOfDay = myCalendar.getTime().getHours();
        int minute = myCalendar.getTime().getMinutes();
        final int second = myCalendar.getTime().getSeconds();


        TimePickerDialog.OnTimeSetListener  timePickerDialog = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendar.set(Calendar.MINUTE,minute);
                myCalendar.set(Calendar.SECOND,second);

                String format ="hh:mm";

                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);

                if(myCalendar.getTime().getHours()==0){

                    hourOfDay = 12;
                }


                timeSelected.onTimeClicked(hourOfDay+ ":"+ minute);
            }
        };


        TimePickerDialog dialog = new TimePickerDialog(context,timePickerDialog,hourOfDay,minute,true);

        dialog.setTitle("");

        dialog.show();


    }



    public static void showDefalTimePicker(final Context context , final TimeSelected timeSelected){


        final Calendar myCalendar = Calendar.getInstance();

         int hourOfDay = 0;
        int minute = myCalendar.getTime().getMinutes();
        final int second = myCalendar.getTime().getSeconds();


        TimePickerDialog.OnTimeSetListener  timePickerDialog = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                myCalendar.getTime().setHours(0);
                myCalendar.set(Calendar.MINUTE,minute);
                myCalendar.set(Calendar.SECOND,second);

                String format ="mm:ss";

                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);

                if(myCalendar.getTime().getHours()==0){

                    hourOfDay = 12;
                }


                timeSelected.onTimeClicked(minute+" mins");
            }
        };


        TimePickerDialog dialog = new TimePickerDialog(context,timePickerDialog,hourOfDay,minute,true);



        dialog.setTitle("");

        dialog.show();


    }




    // Convert Timestamp into Your provided Pattern
    public static String convertStringDateIntoYourPattern(String stringDate, String pattern)
    {
        Date date = null;
        Date formattedDate = null;
        DateFormat format = null;
        String strFormattedDate = null;

        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date = dateFormat.parse(stringDate);

            SimpleDateFormat userDateFormat = new SimpleDateFormat(pattern);
            strFormattedDate = userDateFormat.format(date);
        }
        catch (ParseException e)
        {
            Log.e("Error in date", "parsing " + e);
        }

        return strFormattedDate;
    }

    public static String getFileExt(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }


    public static ArrayList<Date> getDatesBetweenTwoDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try
        {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static ArrayList<String> getDatesBetweenTwoDates(String dateString1, String dateString2,
                                                            String dateFormat)
    {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        Date date1 = null;
        Date date2 = null;

        try
        {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2))
        {
            Date date = cal1.getTime();
            String strDate = df.format(date);
            dates.add(strDate);
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


    public static String getMimeType(String url)
    {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null)
        {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static CharSequence menuIconWithText(Drawable r, String title)
    {
        r.setBounds(0, 0, 48, 48);
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static void overrideFonts(final Context context, final View v)
    {
        try
        {
            if (v instanceof ViewGroup)
            {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++)
                {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            }
            else if (v instanceof TextView)
            {
                int textStyle = ((TextView) v).getTypeface().getStyle();
                applyCustomFont(context, textStyle, v);
            }
            else if (v instanceof EditText)
            {
                int textStyle = ((EditText) v).getTypeface().getStyle();
                applyCustomFont(context, textStyle, v);
            }


        }
        catch (Exception e)
        {
            Log.e("Error in", "setting font" + e);
        }
    }

    private static void applyCustomFont(Context context, int attrs, View v)
    {

        Typeface customFont = selectTypeface(context, attrs);
        if (v instanceof TextView)
        {
            ((TextView) v).setTypeface(customFont);
        }
        else if (v instanceof EditText)
        {
            ((EditText) v).setTypeface(customFont);
        }
    }

    public static Point getSize(Context context)
    {
        final Point point = new Point();
        if (context instanceof AppCompatActivity)
        {
            ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getSize(point);
        }
        else if (context instanceof Activity)
        {
            ((Activity) context).getWindowManager().getDefaultDisplay().getSize(point);
        }
        else if (context instanceof FragmentActivity)
        {
            ((FragmentActivity) context).getWindowManager().getDefaultDisplay().getSize(point);
        }
        return point;
    }

    private static Typeface selectTypeface(Context context, int textStyle)
    {
        /*
         * information about the TextView textStyle:
         * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
         */
        switch (textStyle)
        {
            case Typeface.BOLD: // bold
                return Typeface.createFromAsset(context.getAssets(),
                        "Font/JosefinSans-Bold.ttf");

            case Typeface.ITALIC: // Light
                return Typeface.createFromAsset(context.getAssets(),
                        "Font/JosefinSans-Light.ttf");

            case Typeface.BOLD_ITALIC: // Semi bold
                return Typeface.createFromAsset(context.getAssets(),
                        "Font/JosefinSans-SemiBold.ttf");


            case Typeface.NORMAL: // regular
            default:
                return Typeface.createFromAsset(context.getAssets(),
                        "Font/JosefinSans-Regular.ttf");
        }
    }


    public static String getMd5For(String s)
    {
        try
        {
            // Create MD5 Hash
            byte[] bytesOfMessage = null;
            try
            {
                bytesOfMessage = s.getBytes("UTF-8");
            }
            catch (Exception e)
            {
                bytesOfMessage = s.getBytes();
            }
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytesOfMessage);
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
            {
                String hexValue = Integer.toHexString(0xFF & messageDigest[i]);
                if (hexValue.length() == 1)
                {
                    hexValue = "0" + hexValue;
                }
                hexString.append(hexValue);
            }
            return hexString.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e("MD5", "Exception: ", e);
        }
        return "";
    }

    public static boolean setNumberPickerTextColor(Context context, NumberPicker numberPicker,
                                                   int color)
    {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText)
            {
                try
                {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker))
                            .setTextSize(numberPicker.getContext().getResources()
                                    .getDimension(R.dimen.font_size_16sp));
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((Paint) selectorWheelPaintField.get(numberPicker))
                            .setTypeface(Typeface.createFromAsset(context.getAssets(),
                                    "Font/JosefinSans-Regular.ttf"));
                    ((EditText) child).setTextColor(color);
                    ((EditText) child).setTypeface(Typeface.createFromAsset(context.getAssets(),
                            "Font/JosefinSans-Regular.ttf"));
                    numberPicker.invalidate();
                    return true;
                }
                catch (NoSuchFieldException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static String getStringFromEdittext(EditText editTextView)
    {
        return editTextView.getText().toString().trim();
    }


    public static float dipToPixel(Context context, float dip)
    {
        float px = TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources()
                        .getDisplayMetrics());
        return px;
    }

    public static boolean isValidMobile(String mobile)
    {
        if (!TextUtils.isEmpty(mobile))
        {
            return (mobile).matches("[7-9][0-9]{9}$");
        }
        else
        {
            return false;
        }
    }


    public static void createDirectory(String filePath)
    {
        File file = new File(filePath);
        file.mkdirs();
        Log.e("file path", file + "");
        try
        {
            if (file.mkdir())
            {
                Log.e("Utility", "Directory created");
            }
            else
            {
                Log.e("Utility", "Directory allready exists");
            }
        }
        catch (Exception e)
        {
            Log.e("Error in dir", "" + e);
        }
    }


    public static void showRationalDialog(final Context context, final int requestCode, final int
            requestCodeSetting, final ArrayList<String> permissions, final AlertInterface target)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style
                .Theme_Dialog);
        builder.setTitle(context.getResources().getString(R.string.permission_dialog_title));
        builder.setCancelable(false);
        builder.setMessage(context.getResources().getString(R.string
                .multiple_permission_message));
        builder.setPositiveButton(context.getResources().getString(R.string.grant_label),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        target.onButtonClicked(true);


                    }
                });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel_label),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        target.onButtonClicked(false);


                    }
                });
        AlertDialog dialog = builder.create();
        if (!((Activity) context).isFinishing())
        {
            dialog.show();
        }

    }

    public static boolean showWarningDialog(Context mCtx, final int requestCodeSetting,
                                            final AlertInterface target)
    {
        final Context localContext = mCtx;

        AlertDialog.Builder builder = new AlertDialog.Builder(localContext,R.style.Theme_Dialog);
        builder.setTitle(localContext.getResources().getString(R.string.permission_dialog_title));
        builder.setMessage(localContext.getResources().getString(R.string
                .permission_denied_message));
        builder.setCancelable(false);
        builder.setPositiveButton(localContext.getResources().getString(R.string.grant_label), new
                DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        target.onButtonClicked(true);
                    }
                });
        builder.setNegativeButton(localContext.getResources().getString(R.string.ok_label), new
                DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        target.onButtonClicked(false);
                    }
                });
        AlertDialog dialog1 = builder.create();
        if (!((Activity) localContext).isFinishing())
        {
            dialog1.show();
        }
        return grantFlagForPermissionWarning;
    }


    public static ArrayList<String> getPermissionList()
    {

        ArrayList<String> permissions = new ArrayList<String>();
        try
        {
            //Permission Array
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissions.add(Manifest.permission.INTERNET);
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE);
            permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
            permissions.add(Manifest.permission.READ_PHONE_STATE);
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.GET_ACCOUNTS);
            permissions.add(Manifest.permission.READ_CONTACTS);
            permissions.add(Manifest.permission.CAMERA);


        }
        catch (Exception e)
        {
            Log.e("Excep in Permission", "" + e);
        }
        return permissions;
    }

    public static void getConfirmDialog(final Context mContext,
                                        final String title, final String msg,
                                        final String positiveBtnCaption, final String
                                                negativeBtnCaption,
                                        final boolean isCancelable, final AlertInterface target)
    {

        ((Activity) mContext).runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style
                        .Theme_Dialog);


                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(
                        imageResource);
               // .setIcon(image);
                if (!title.matches(""))
                {
                    builder.setTitle(title);

                }
                builder.setMessage(msg)
                        .setCancelable(false);
                if (!negativeBtnCaption.matches(""))
                {
                    builder.setPositiveButton(positiveBtnCaption,
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,
                                                    int id)
                                {
                                    target.onButtonClicked(true);
                                }
                            })
                            .setNegativeButton(negativeBtnCaption,
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int id)
                                        {
                                            target.onButtonClicked(false);
                                        }
                                    });
                }
                else
                {
                    builder.setPositiveButton(positiveBtnCaption,
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,
                                                    int id)
                                {
                                    target.onButtonClicked(true);
                                }
                            });

                }


                AlertDialog alert = builder.create();
                alert.setCancelable(isCancelable);
                alert.show();
                if (isCancelable)
                {
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener()
                    {

                        @Override
                        public void onCancel(DialogInterface arg0)
                        {
                            target.onButtonClicked(false);
                        }
                    });
                }
            }
        });

    }

    public static HashMap<String, String> getAllTypesOfMIME()
    {
        HashMap<String, String> types = new HashMap<>();
        types.put("323", "text/h323");
        types.put("3g2", "video/3gpp2");
        types.put("3gp", "video/3gpp");
        types.put("3gp2", "video/3gpp2");
        types.put("3gpp", "video/3gpp");
        types.put("7z", "application/x-7z-compressed");
        types.put("aa", "audio/audible");
        types.put("AAC", "audio/aac");
        types.put("aaf", "application/octet-stream");
        types.put("aax", "audio/vnd.audible.aax");
        types.put("ac3", "audio/ac3");
        types.put("aca", "application/octet-stream");
        types.put("accda", "application/msaccess.addin");
        types.put("accdb", "application/msaccess");
        types.put("accdc", "application/msaccess.cab");
        types.put("accde", "application/msaccess");
        types.put("accdr", "application/msaccess.runtime");
        types.put("accdt", "application/msaccess");
        types.put("accdw", "application/msaccess.webapplication");
        types.put("accft", "application/msaccess.ftemplate");
        types.put("acx", "application/internet-property-stream");
        types.put("AddIn", "text/xml");
        types.put("ade", "application/msaccess");
        types.put("adobebridge", "application/x-bridge-url");
        types.put("adp", "application/msaccess");
        types.put("ADT", "audio/vnd.dlna.adts");
        types.put("ADTS", "audio/aac");
        types.put("afm", "application/octet-stream");
        types.put("ai", "application/postscript");
        types.put("aif", "audio/aiff");
        types.put("aifc", "audio/aiff");
        types.put("aiff", "audio/aiff");
        types.put("air", "application/vnd.adobe.air-application-installer-package+zip");
        types.put("amc", "application/mpeg");
        types.put("anx", "application/annodex");
        types.put("apk", "application/vnd.android.package-archive");
        types.put("application", "application/x-ms-application");
        types.put("art", "image/x-jg");
        types.put("asa", "application/xml");
        types.put("asax", "application/xml");
        types.put("ascx", "application/xml");
        types.put("asd", "application/octet-stream");
        types.put("asf", "video/x-ms-asf");
        types.put("ashx", "application/xml");
        types.put("asi", "application/octet-stream");
        types.put("asm", "text/plain");
        types.put("asmx", "application/xml");
        types.put("aspx", "application/xml");
        types.put("asr", "video/x-ms-asf");
        types.put("asx", "video/x-ms-asf");
        types.put("atom", "application/atom+xml");
        types.put("au", "audio/basic");
        types.put("avi", "video/x-msvideo");
        types.put("axa", "audio/annodex");
        types.put("axs", "application/olescript");
        types.put("axv", "video/annodex");
        types.put("bas", "text/plain");
        types.put("bcpio", "application/x-bcpio");
        types.put("bin", "application/octet-stream");
        types.put("bmp", "image/bmp");
        types.put("c", "text/plain");
        types.put("cab", "application/octet-stream");
        types.put("caf", "audio/x-caf");
        types.put("calx", "application/vnd.ms-office.calx");
        types.put("cat", "application/vnd.ms-pki.seccat");
        types.put("cc", "text/plain");
        types.put("cd", "text/plain");
        types.put("cdda", "audio/aiff");
        types.put("cdf", "application/x-cdf");
        types.put("cer", "application/x-x509-ca-cert");
        types.put("cfg", "text/plain");
        types.put("chm", "application/octet-stream");
        types.put("class", "application/x-java-applet");
        types.put("clp", "application/x-msclip");
        types.put("cmd", "text/plain");
        types.put("cmx", "image/x-cmx");
        types.put("cnf", "text/plain");
        types.put("cod", "image/cis-cod");
        types.put("config", "application/xml");
        types.put("contact", "text/x-ms-contact");
        types.put("coverage", "application/xml");
        types.put("cpio", "application/x-cpio");
        types.put("cpp", "text/plain");
        types.put("crd", "application/x-mscardfile");
        types.put("crl", "application/pkix-crl");
        types.put("crt", "application/x-x509-ca-cert");
        types.put("cs", "text/plain");
        types.put("csdproj", "text/plain");
        types.put("csh", "application/x-csh");
        types.put("csproj", "text/plain");
        types.put("css", "text/css");
        types.put("csv", "text/csv");
        types.put("cur", "application/octet-stream");
        types.put("cxx", "text/plain");
        types.put("dat", "application/octet-stream");
        types.put("datasource", "application/xml");
        types.put("dbproj", "text/plain");
        types.put("dcr", "application/x-director");
        types.put("def", "text/plain");
        types.put("deploy", "application/octet-stream");
        types.put("der", "application/x-x509-ca-cert");
        types.put("dgml", "application/xml");
        types.put("dib", "image/bmp");
        types.put("dif", "video/x-dv");
        types.put("dir", "application/x-director");
        types.put("disco", "text/xml");
        types.put("divx", "video/divx");
        types.put("dll", "application/x-msdownload");
        types.put("dll.config", "text/xml");
        types.put("dlm", "text/dlm");
        types.put("doc", "application/msword");
        types.put("docm", "application/vnd.ms-word.document.macroEnabled.12");
        types.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        types.put("dot", "application/msword");
        types.put("dotm", "application/vnd.ms-word.template.macroEnabled.12");
        types.put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        types.put("dsp", "application/octet-stream");
        types.put("dsw", "text/plain");
        types.put("dtd", "text/xml");
        types.put("dtsConfig", "text/xml");
        types.put("dv", "video/x-dv");
        types.put("dvi", "application/x-dvi");
        types.put("dwf", "drawing/x-dwf");
        types.put("dwp", "application/octet-stream");
        types.put("dxr", "application/x-director");
        types.put("eml", "message/rfc822");
        types.put("emz", "application/octet-stream");
        types.put("eot", "application/vnd.ms-fontobject");
        types.put("eps", "application/postscript");
        types.put("etl", "application/etl");
        types.put("etx", "text/x-setext");
        types.put("evy", "application/envoy");
        types.put("exe", "application/octet-stream");
        types.put("exe.config", "text/xml");
        types.put("fdf", "application/vnd.fdf");
        types.put("fif", "application/fractals");
        types.put("filters", "application/xml");
        types.put("fla", "application/octet-stream");
        types.put("flac", "audio/flac");
        types.put("flr", "x-world/x-vrml");
        types.put("flv", "video/x-flv");
        types.put("fsscript", "application/fsharp-script");
        types.put("fsx", "application/fsharp-script");
        types.put("generictest", "application/xml");
        types.put("gif", "image/gif");
        types.put("group", "text/x-ms-group");
        types.put("gsm", "audio/x-gsm");
        types.put("gtar", "application/x-gtar");
        types.put("gz", "application/x-gzip");
        types.put("h", "text/plain");
        types.put("hdf", "application/x-hdf");
        types.put("hdml", "text/x-hdml");
        types.put("hhc", "application/x-oleobject");
        types.put("hhk", "application/octet-stream");
        types.put("hhp", "application/octet-stream");
        types.put("hlp", "application/winhlp");
        types.put("hpp", "text/plain");
        types.put("hqx", "application/mac-binhex40");
        types.put("hta", "application/hta");
        types.put("htc", "text/x-component");
        types.put("htm", "text/html");
        types.put("html", "text/html");
        types.put("htt", "text/webviewhtml");
        types.put("hxa", "application/xml");
        types.put("hxc", "application/xml");
        types.put("hxd", "application/octet-stream");
        types.put("hxe", "application/xml");
        types.put("hxf", "application/xml");
        types.put("hxh", "application/octet-stream");
        types.put("hxi", "application/octet-stream");
        types.put("hxk", "application/xml");
        types.put("hxq", "application/octet-stream");
        types.put("hxr", "application/octet-stream");
        types.put("hxs", "application/octet-stream");
        types.put("hxt", "text/html");
        types.put("hxv", "application/xml");
        types.put("hxw", "application/octet-stream");
        types.put("hxx", "text/plain");
        types.put("i", "text/plain");
        types.put("ico", "image/x-icon");
        types.put("ics", "application/octet-stream");
        types.put("idl", "text/plain");
        types.put("ief", "image/ief");
        types.put("iii", "application/x-iphone");
        types.put("inc", "text/plain");
        types.put("inf", "application/octet-stream");
        types.put("ini", "text/plain");
        types.put("inl", "text/plain");
        types.put("ins", "application/x-internet-signup");
        types.put("ipa", "application/x-itunes-ipa");
        types.put("ipg", "application/x-itunes-ipg");
        types.put("ipproj", "text/plain");
        types.put("ipsw", "application/x-itunes-ipsw");
        types.put("iqy", "text/x-ms-iqy");
        types.put("isp", "application/x-internet-signup");
        types.put("ite", "application/x-itunes-ite");
        types.put("itlp", "application/x-itunes-itlp");
        types.put("itms", "application/x-itunes-itms");
        types.put("itpc", "application/x-itunes-itpc");
        types.put("IVF", "video/x-ivf");
        types.put("jar", "application/java-archive");
        types.put("java", "application/octet-stream");
        types.put("jck", "application/liquidmotion");
        types.put("jcz", "application/liquidmotion");
        types.put("jfif", "image/pjpeg");
        types.put("jnlp", "application/x-java-jnlp-file");
        types.put("jpb", "application/octet-stream");
        types.put("jpe", "image/jpeg");
        types.put("jpeg", "image/jpeg");
        types.put("jpg", "image/jpeg");
        types.put("js", "application/javascript");
        types.put("json", "application/json");
        types.put("jsx", "text/jscript");
        types.put("jsxbin", "text/plain");
        types.put("latex", "application/x-latex");
        types.put("library-ms", "application/windows-library+xml");
        types.put("lit", "application/x-ms-reader");
        types.put("loadtest", "application/xml");
        types.put("lpk", "application/octet-stream");
        types.put("lsf", "video/x-la-asf");
        types.put("lst", "text/plain");
        types.put("lsx", "video/x-la-asf");
        types.put("lzh", "application/octet-stream");
        types.put("m13", "application/x-msmediaview");
        types.put("m14", "application/x-msmediaview");
        types.put("m1v", "video/mpeg");
        types.put("m2t", "video/vnd.dlna.mpeg-tts");
        types.put("m2ts", "video/vnd.dlna.mpeg-tts");
        types.put("m2v", "video/mpeg");
        types.put("m3u", "audio/x-mpegurl");
        types.put("m3u8", "audio/x-mpegurl");
        types.put("m4a", "audio/m4a");
        types.put("m4b", "audio/m4b");
        types.put("m4p", "audio/m4p");
        types.put("m4r", "audio/x-m4r");
        types.put("m4v", "video/x-m4v");
        types.put("mac", "image/x-macpaint");
        types.put("mak", "text/plain");
        types.put("man", "application/x-troff-man");
        types.put("manifest", "application/x-ms-manifest");
        types.put("map", "text/plain");
        types.put("master", "application/xml");
        types.put("mda", "application/msaccess");
        types.put("mdb", "application/x-msaccess");
        types.put("mde", "application/msaccess");
        types.put("mdp", "application/octet-stream");
        types.put("me", "application/x-troff-me");
        types.put("mfp", "application/x-shockwave-flash");
        types.put("mht", "message/rfc822");
        types.put("mhtml", "message/rfc822");
        types.put("mid", "audio/mid");
        types.put("midi", "audio/mid");
        types.put("mix", "application/octet-stream");
        types.put("mk", "text/plain");
        types.put("mmf", "application/x-smaf");
        types.put("mno", "text/xml");
        types.put("mny", "application/x-msmoney");
        types.put("mod", "video/mpeg");
        types.put("mov", "video/quicktime");
        types.put("movie", "video/x-sgi-movie");
        types.put("mp2", "video/mpeg");
        types.put("mp2v", "video/mpeg");
        types.put("mp3", "audio/mpeg");
        types.put("mp4", "video/mp4");
        types.put("mp4v", "video/mp4");
        types.put("mpa", "video/mpeg");
        types.put("mpe", "video/mpeg");
        types.put("mpeg", "video/mpeg");
        types.put("mpf", "application/vnd.ms-mediapackage");
        types.put("mpg", "video/mpeg");
        types.put("mpp", "application/vnd.ms-project");
        types.put("mpv2", "video/mpeg");
        types.put("mqv", "video/quicktime");
        types.put("ms", "application/x-troff-ms");
        types.put("msi", "application/octet-stream");
        types.put("mso", "application/octet-stream");
        types.put("mts", "video/vnd.dlna.mpeg-tts");
        types.put("mtx", "application/xml");
        types.put("mvb", "application/x-msmediaview");
        types.put("mvc", "application/x-miva-compiled");
        types.put("mxp", "application/x-mmxp");
        types.put("nc", "application/x-netcdf");
        types.put("nsc", "video/x-ms-asf");
        types.put("nws", "message/rfc822");
        types.put("ocx", "application/octet-stream");
        types.put("oda", "application/oda");
        types.put("odb", "application/vnd.oasis.opendocument.database");
        types.put("odc", "application/vnd.oasis.opendocument.chart");
        types.put("odf", "application/vnd.oasis.opendocument.formula");
        types.put("odg", "application/vnd.oasis.opendocument.graphics");
        types.put("odh", "text/plain");
        types.put("odi", "application/vnd.oasis.opendocument.image");
        types.put("odl", "text/plain");
        types.put("odm", "application/vnd.oasis.opendocument.text-master");
        types.put("odp", "application/vnd.oasis.opendocument.presentation");
        types.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        types.put("odt", "application/vnd.oasis.opendocument.text");
        types.put("oga", "audio/ogg");
        types.put("ogg", "audio/ogg");
        types.put("ogv", "video/ogg");
        types.put("ogx", "application/ogg");
        types.put("one", "application/onenote");
        types.put("onea", "application/onenote");
        types.put("onepkg", "application/onenote");
        types.put("onetmp", "application/onenote");
        types.put("onetoc", "application/onenote");
        types.put("onetoc2", "application/onenote");
        types.put("opus", "audio/ogg");
        types.put("orderedtest", "application/xml");
        types.put("osdx", "application/opensearchdescription+xml");
        types.put("otf", "application/font-sfnt");
        types.put("otg", "application/vnd.oasis.opendocument.graphics-template");
        types.put("oth", "application/vnd.oasis.opendocument.text-web");
        types.put("otp", "application/vnd.oasis.opendocument.presentation-template");
        types.put("ots", "application/vnd.oasis.opendocument.spreadsheet-template");
        types.put("ott", "application/vnd.oasis.opendocument.text-template");
        types.put("oxt", "application/vnd.openofficeorg.extension");
        types.put("p10", "application/pkcs10");
        types.put("p12", "application/x-pkcs12");
        types.put("p7b", "application/x-pkcs7-certificates");
        types.put("p7c", "application/pkcs7-mime");
        types.put("p7m", "application/pkcs7-mime");
        types.put("p7r", "application/x-pkcs7-certreqresp");
        types.put("p7s", "application/pkcs7-signature");
        types.put("pbm", "image/x-portable-bitmap");
        types.put("pcast", "application/x-podcast");
        types.put("pct", "image/pict");
        types.put("pcx", "application/octet-stream");
        types.put("pcz", "application/octet-stream");
        types.put("pdf", "application/pdf");
        types.put("pfb", "application/octet-stream");
        types.put("pfm", "application/octet-stream");
        types.put("pfx", "application/x-pkcs12");
        types.put("pgm", "image/x-portable-graymap");
        types.put("pic", "image/pict");
        types.put("pict", "image/pict");
        types.put("pkgdef", "text/plain");
        types.put("pkgundef", "text/plain");
        types.put("pko", "application/vnd.ms-pki.pko");
        types.put("pls", "audio/scpls");
        types.put("pma", "application/x-perfmon");
        types.put("pmc", "application/x-perfmon");
        types.put("pml", "application/x-perfmon");
        types.put("pmr", "application/x-perfmon");
        types.put("pmw", "application/x-perfmon");
        types.put("png", "image/png");
        types.put("pnm", "image/x-portable-anymap");
        types.put("pnt", "image/x-macpaint");
        types.put("pntg", "image/x-macpaint");
        types.put("pnz", "image/png");
        types.put("pot", "application/vnd.ms-powerpoint");
        types.put("potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
        types.put("potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
        types.put("ppa", "application/vnd.ms-powerpoint");
        types.put("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
        types.put("ppm", "image/x-portable-pixmap");
        types.put("pps", "application/vnd.ms-powerpoint");
        types.put("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
        types.put("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        types.put("ppt", "application/vnd.ms-powerpoint");
        types.put("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        types.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        types.put("prf", "application/pics-rules");
        types.put("prm", "application/octet-stream");
        types.put("prx", "application/octet-stream");
        types.put("ps", "application/postscript");
        types.put("psc1", "application/PowerShell");
        types.put("psd", "application/octet-stream");
        types.put("psess", "application/xml");
        types.put("psm", "application/octet-stream");
        types.put("psp", "application/octet-stream");
        types.put("pub", "application/x-mspublisher");
        types.put("pwz", "application/vnd.ms-powerpoint");
        types.put("qht", "text/x-html-insertion");
        types.put("qhtm", "text/x-html-insertion");
        types.put("qt", "video/quicktime");
        types.put("qti", "image/x-quicktime");
        types.put("qtif", "image/x-quicktime");
        types.put("qtl", "application/x-quicktimeplayer");
        types.put("qxd", "application/octet-stream");
        types.put("ra", "audio/x-pn-realaudio");
        types.put("ram", "audio/x-pn-realaudio");
        types.put("rar", "application/x-rar-compressed");
        types.put("ras", "image/x-cmu-raster");
        types.put("rat", "application/rat-file");
        types.put("rc", "text/plain");
        types.put("rc2", "text/plain");
        types.put("rct", "text/plain");
        types.put("rdlc", "application/xml");
        types.put("reg", "text/plain");
        types.put("resx", "application/xml");
        types.put("rf", "image/vnd.rn-realflash");
        types.put("rgb", "image/x-rgb");
        types.put("rgs", "text/plain");
        types.put("rm", "application/vnd.rn-realmedia");
        types.put("rmi", "audio/mid");
        types.put("rmp", "application/vnd.rn-rn_music_package");
        types.put("roff", "application/x-troff");
        types.put("rpm", "audio/x-pn-realaudio-plugin");
        types.put("rqy", "text/x-ms-rqy");
        types.put("rtf", "application/rtf");
        types.put("rtx", "text/richtext");
        types.put("ruleset", "application/xml");
        types.put("s", "text/plain");
        types.put("safariextz", "application/x-safari-safariextz");
        types.put("scd", "application/x-msschedule");
        types.put("scr", "text/plain");
        types.put("sct", "text/scriptlet");
        types.put("sd2", "audio/x-sd2");
        types.put("sdp", "application/sdp");
        types.put("sea", "application/octet-stream");
        types.put("searchConnector-ms", "application/windows-search-connector+xml");
        types.put("setpay", "application/set-payment-initiation");
        types.put("setreg", "application/set-registration-initiation");
        types.put("settings", "application/xml");
        types.put("sgimb", "application/x-sgimb");
        types.put("sgml", "text/sgml");
        types.put("sh", "application/x-sh");
        types.put("shar", "application/x-shar");
        types.put("shtml", "text/html");
        types.put("sit", "application/x-stuffit");
        types.put("sitemap", "application/xml");
        types.put("skin", "application/xml");
        types.put("sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12");
        types.put("sldx", "application/vnd.openxmlformats-officedocument.presentationml.slide");
        types.put("slk", "application/vnd.ms-excel");
        types.put("sln", "text/plain");
        types.put("slupkg-ms", "application/x-ms-license");
        types.put("smd", "audio/x-smd");
        types.put("smi", "application/octet-stream");
        types.put("smx", "audio/x-smd");
        types.put("smz", "audio/x-smd");
        types.put("snd", "audio/basic");
        types.put("snippet", "application/xml");
        types.put("snp", "application/octet-stream");
        types.put("sol", "text/plain");
        types.put("sor", "text/plain");
        types.put("spc", "application/x-pkcs7-certificates");
        types.put("spl", "application/futuresplash");
        types.put("spx", "audio/ogg");
        types.put("src", "application/x-wais-source");
        types.put("srf", "text/plain");
        types.put("SSISDeploymentManifest", "text/xml");
        types.put("ssm", "application/streamingmedia");
        types.put("sst", "application/vnd.ms-pki.certstore");
        types.put("stl", "application/vnd.ms-pki.stl");
        types.put("sv4cpio", "application/x-sv4cpio");
        types.put("sv4crc", "application/x-sv4crc");
        types.put("svc", "application/xml");
        types.put("svg", "image/svg+xml");
        types.put("swf", "application/x-shockwave-flash");
        types.put("t", "application/x-troff");
        types.put("tar", "application/x-tar");
        types.put("tcl", "application/x-tcl");
        types.put("testrunconfig", "application/xml");
        types.put("testsettings", "application/xml");
        types.put("tex", "application/x-tex");
        types.put("texi", "application/x-texinfo");
        types.put("texinfo", "application/x-texinfo");
        types.put("tgz", "application/x-compressed");
        types.put("thmx", "application/vnd.ms-officetheme");
        types.put("thn", "application/octet-stream");
        types.put("tif", "image/tiff");
        types.put("tiff", "image/tiff");
        types.put("tlh", "text/plain");
        types.put("tli", "text/plain");
        types.put("toc", "application/octet-stream");
        types.put("tr", "application/x-troff");
        types.put("trm", "application/x-msterminal");
        types.put("trx", "application/xml");
        types.put("ts", "video/vnd.dlna.mpeg-tts");
        types.put("tsv", "text/tab-separated-values");
        types.put("ttf", "application/font-sfnt");
        types.put("tts", "video/vnd.dlna.mpeg-tts");
        types.put("txt", "text/plain");
        types.put("u32", "application/octet-stream");
        types.put("uls", "text/iuls");
        types.put("user", "text/plain");
        types.put("ustar", "application/x-ustar");
        types.put("vb", "text/plain");
        types.put("vbdproj", "text/plain");
        types.put("vbk", "video/mpeg");
        types.put("vbproj", "text/plain");
        types.put("vbs", "text/vbscript");
        types.put("vcf", "text/x-vcard");
        types.put("vcproj", "application/xml");
        types.put("vcs", "text/plain");
        types.put("vcxproj", "application/xml");
        types.put("vddproj", "text/plain");
        types.put("vdp", "text/plain");
        types.put("vdproj", "text/plain");
        types.put("vdx", "application/vnd.ms-visio.viewer");
        types.put("vml", "text/xml");
        types.put("vscontent", "application/xml");
        types.put("vsct", "text/xml");
        types.put("vsd", "application/vnd.visio");
        types.put("vsi", "application/ms-vsi");
        types.put("vsix", "application/vsix");
        types.put("vsixlangpack", "text/xml");
        types.put("vsixmanifest", "text/xml");
        types.put("vsmdi", "application/xml");
        types.put("vspscc", "text/plain");
        types.put("vss", "application/vnd.visio");
        types.put("vsscc", "text/plain");
        types.put("vssettings", "text/xml");
        types.put("vssscc", "text/plain");
        types.put("vst", "application/vnd.visio");
        types.put("vstemplate", "text/xml");
        types.put("vsto", "application/x-ms-vsto");
        types.put("vsw", "application/vnd.visio");
        types.put("vsx", "application/vnd.visio");
        types.put("vtx", "application/vnd.visio");
        types.put("wav", "audio/wav");
        types.put("wave", "audio/wav");
        types.put("wax", "audio/x-ms-wax");
        types.put("wbk", "application/msword");
        types.put("wbmp", "image/vnd.wap.wbmp");
        types.put("wcm", "application/vnd.ms-works");
        types.put("wdb", "application/vnd.ms-works");
        types.put("wdp", "image/vnd.ms-photo");
        types.put("webarchive", "application/x-safari-webarchive");
        types.put("webm", "video/webm");
        types.put("webp", "image/webp");
        types.put("webtest", "application/xml");
        types.put("wiq", "application/xml");
        types.put("wiz", "application/msword");
        types.put("wks", "application/vnd.ms-works");
        types.put("WLMP", "application/wlmoviemaker");
        types.put("wlpginstall", "application/x-wlpg-detect");
        types.put("wlpginstall3", "application/x-wlpg3-detect");
        types.put("wm", "video/x-ms-wm");
        types.put("wma", "audio/x-ms-wma");
        types.put("wmd", "application/x-ms-wmd");
        types.put("wmf", "application/x-msmetafile");
        types.put("wml", "text/vnd.wap.wml");
        types.put("wmlc", "application/vnd.wap.wmlc");
        types.put("wmls", "text/vnd.wap.wmlscript");
        types.put("wmlsc", "application/vnd.wap.wmlscriptc");
        types.put("wmp", "video/x-ms-wmp");
        types.put("wmv", "video/x-ms-wmv");
        types.put("wmx", "video/x-ms-wmx");
        types.put("wmz", "application/x-ms-wmz");
        types.put("woff", "application/font-woff");
        types.put("wpl", "application/vnd.ms-wpl");
        types.put("wps", "application/vnd.ms-works");
        types.put("wri", "application/x-mswrite");
        types.put("wrl", "x-world/x-vrml");
        types.put("wrz", "x-world/x-vrml");
        types.put("wsc", "text/scriptlet");
        types.put("wsdl", "text/xml");
        types.put("wvx", "video/x-ms-wvx");
        types.put("x", "application/directx");
        types.put("xaf", "x-world/x-vrml");
        types.put("xaml", "application/xaml+xml");
        types.put("xap", "application/x-silverlight-app");
        types.put("xbap", "application/x-ms-xbap");
        types.put("xbm", "image/x-xbitmap");
        types.put("xdr", "text/plain");
        types.put("xht", "application/xhtml+xml");
        types.put("xhtml", "application/xhtml+xml");
        types.put("xla", "application/vnd.ms-excel");
        types.put("xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
        types.put("xlc", "application/vnd.ms-excel");
        types.put("xld", "application/vnd.ms-excel");
        types.put("xlk", "application/vnd.ms-excel");
        types.put("xll", "application/vnd.ms-excel");
        types.put("xlm", "application/vnd.ms-excel");
        types.put("xls", "application/vnd.ms-excel");
        types.put("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
        types.put("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
        types.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        types.put("xlt", "application/vnd.ms-excel");
        types.put("xltm", "application/vnd.ms-excel.template.macroEnabled.12");
        types.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        types.put("xlw", "application/vnd.ms-excel");
        types.put("xml", "text/xml");
        types.put("xmta", "application/xml");
        types.put("xof", "x-world/x-vrml");
        types.put("XOML", "text/plain");
        types.put("xpm", "image/x-xpixmap");
        types.put("xps", "application/vnd.ms-xpsdocument");
        types.put("xrm-ms", "text/xml");
        types.put("xsc", "application/xml");
        types.put("xsd", "text/xml");
        types.put("xsf", "text/xml");
        types.put("xsl", "text/xml");
        types.put("xslt", "text/xml");
        types.put("xsn", "application/octet-stream");
        types.put("xss", "application/xml");
        types.put("xspf", "application/xspf+xml");
        types.put("xtp", "application/octet-stream");
        types.put("xwd", "image/x-xwindowdump");
        types.put("z", "application/x-compress");
        types.put("zip", "application/zip");

        return types;
    }


    public static String getDeviceIMEI(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();

    }

    public static String getCurrentTimeStamp(String timeStamp)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeStamp);

        String format = simpleDateFormat.format(new Date());
        return format;
    }


    public static boolean fileExistOrNot(String filePath)
    {
        File f = new File(filePath);
        if (!f.exists() && !f.isDirectory())
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public static String removeLastCharacterFromString(String str)
    {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',')
        {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


    public static void showSnackBar(String message, View coordinatorLayout)
    {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    public static String getStringFromEditText(EditText editText)
    {
        if (editText != null)
        {
            return editText.getText().toString().trim();
        }
        else
        {
            return null;
        }
    }

    public static String getStringFromTextView(TextView textView)
    {
        if (textView != null)
        {
            return textView.getText().toString().trim();
        }
        else
        {
            return null;
        }
    }

    public static void ellipsizeTextView(final TextView textView)
    {
        ViewTreeObserver vto = textView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {
                ViewTreeObserver obs = textView.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (textView.getLineCount() > 2)
                {
                    int lineEndIndex = textView.getLayout().getLineEnd(2);
                    String text = textView.getText().subSequence(0, lineEndIndex - 3) + "...";
                    textView.setText(text);
                }
            }
        });
    }

    //conversion method
    public static float convertPixelsToDp(float px, Context context)
    {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px * displayMetrics.density;
    }

    public static void launchSimpleActivity(Context fromActivity, Class toActivty)
    {
        Intent intent = new Intent(fromActivity, toActivty);
        fromActivity.startActivity(intent);
    }

    public static void launchActivityWithParameters(Context fromActivity, Class toActivty,
                                                    Bundle bundle,
                                                    ArrayList<Integer> flags)
    {
        Intent intent = new Intent(fromActivity, toActivty);
        intent.putExtra("Bundle", bundle);
        if (flags != null)
        {
            if (!flags.isEmpty())
            {
                for (int i = 0; i < flags.size(); i++)
                {
                    intent.addFlags(flags.get(i));
                }
            }
        }
        fromActivity.startActivity(intent);
    }

    public static String makeFirstLetterCapital(String rawString)
    {
        if (rawString != null)
        {
            return rawString.substring(0, 1).toUpperCase() + rawString.substring(1).toLowerCase();
        }
        else
        {
            return null;
        }
    }


    public static String getFilename()
    {
        File file = new File(CommonVars.DOWNLOADED_FILESPATH_CAMERA);
        if (!file.exists())
        {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    /**
     * Creating file uri to store image/video
     */
    public static Uri getOutputMediaFileUri(Context context, int type)
    {
        //return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(context,
                context.getPackageName() + ".provider",
                getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type)
    {

        // External sdcard location
        File mediaStorageDir = new File(CommonVars.DOWNLOADED_FILESPATH_CAMERA);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                Log.e("I2I", "Oops! Failed create "
                        + CommonVars.DOWNLOADED_FILESPATH_CAMERA + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");


        }
        else if (type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        }
        else
        {
            return null;
        }
        mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();
        return mediaFile;
    }

    public static String compressImage(Context context, String imageUri)
    {

        String filePath = getRealPathFromURI(context, imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;

        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth)
        {
            if (imgRatio < maxRatio)
            {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            }
            else if (imgRatio > maxRatio)
            {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            }
            else
            {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try
        {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        }
        catch (OutOfMemoryError exception)
        {
            exception.printStackTrace();

        }
        try
        {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        }
        catch (OutOfMemoryError exception)
        {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2,
                new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try
        {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.e("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6)
            {
                matrix.postRotate(90);
                Log.e("EXIF", "Exif: " + orientation);
            }
            else if (orientation == 3)
            {
                matrix.postRotate(180);
                Log.e("EXIF", "Exif: " + orientation);
            }
            else if (orientation == 8)
            {
                matrix.postRotate(270);
                Log.e("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        }
        catch (IOException e)
        {
            Log.e("Error in", "" + e);
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try
        {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        }
        catch (FileNotFoundException e)
        {
            Log.e("Error in", "" + e);
        }

        return filename;

    }

    public static void captureImage(Activity context, int resultCode)
    {
        fileUri = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(context, MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent

        context.startActivityForResult(intent, resultCode);
    }

    public static void captureImage(Fragment context, int resultCode)
    {

        fileUri = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        fileUri = getOutputMediaFileUri(context.getActivity(), MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent

        context.startActivityForResult(intent, resultCode);
    }

    public static void captureVideo(Fragment context, int resultCode)
    {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, mMaxLength);
        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, mQuality);
        takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, mMaxSize * K * K);
        context.startActivityForResult(takeVideoIntent, resultCode);


    }

    public static void galleryImage(Activity context, int resultCode)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        context.startActivityForResult(galleryIntent, resultCode);
    }

    public static void galleryImage(Fragment context, int resultCode)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("*/*");
        context.startActivityForResult(galleryIntent, resultCode);
    }

    public static void galleryVideo(Fragment context, int resultCode)
    {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, "Select Video"), resultCode);

    }

    private static String getRealPathFromURI(Context context, String contentURI)
    {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null)
        {
            return contentUri.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
        {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static File saveToInternalStorage(Context context, String filePath, String location)
    {

        String path = null;
        File mypath = null;
        try
        {
            if (filePath != null)
            {
                File image = new File(filePath);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                ContextWrapper cw = new ContextWrapper(context);
                // path to /data/data/yourapp/app_data/imageDir

                //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                // Create imageDir


                File createDir = new File(location + File.separator);
                if (!createDir.exists())
                {
                    createDir.mkdirs();
                }
                String timeStamp =
                        new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                                .format(new Date());
                String Path = location;
                mypath = new File(Path, timeStamp + ".jpg");

                path = mypath.getAbsolutePath();

                FileOutputStream fos = null;
                try
                {
                    fos = new FileOutputStream(mypath);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        fos.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                Log.e("Error in Capture", "Captured Failed");
            }
        }
        catch (Exception e)
        {
            Log.e("Error in Capture", "" + e);
        }
        return mypath;
    }


    public static File saveToInternalStorageVideo(Context context, String filePath, String location)
    {
        File mypath = null;
        String sourceFilename = filePath;
        String destinationFilename = null;
        File createDir = new File(location + File.separator);
        if (!createDir.exists())
        {
            createDir.mkdir();
        }

        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String Path = location;
        mypath = new File(Path, timeStamp + ".mp4");

        destinationFilename = mypath.getAbsolutePath();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try
        {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do
            {
                bos.write(buf);
            } while (bis.read(buf) != -1);
        }
        catch (IOException e)
        {
            Log.e("VIdeo Error", "" + e);
        }
        finally
        {
            try
            {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return mypath;
    }



   /* public static void logoutUserSucessfully(Activity context, AccountManager accountManager)
    {
        ArrayList<Integer> flagArrayList = new ArrayList<Integer>();
        flagArrayList.add(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        flagArrayList.add(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        flagArrayList.add(Intent.FLAG_ACTIVITY_NEW_TASK);

        Utility.launchActivityWithParameters(context,
                LoginActivity.class, null, flagArrayList);

        accountManager.logoutUser();
        accountManager.setLoginUser(null);
        context.finish();


    }*/



    public static SpannableStringBuilder prepareStringWithRedAsterik(String simple)
    {

        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;

    }


    public static void makeTextViewResizable(final TextView tv, final int maxLine,
                                             final String expandText, final OnClicked clicked)
    {

        try
        {
            if (tv.getTag() == null)
            {
                tv.setTag(tv.getText());
            }
            ViewTreeObserver vto = tv.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
            {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout()
                {

                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                    if (tv.getLineCount() >= maxLine)
                    {
                        try
                        {
                            int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                            String text = tv.getText().subSequence(0, lineEndIndex - expandText
                                    .length() + 1) + " " + expandText;

                            tv.setText(spannableString(text, expandText, clicked));
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }


                }


            });
        }
        catch (Exception e)
        {
            Log.e("Error in ", "Text Spannable" + e);
        }

    }

    public static String makeTextEllipsize(final int afterCharacter,
                                           final String stringToEllipsize)
    {
        String returnString = "";

        try
        {
            if (stringToEllipsize.length() > afterCharacter)
            {
                returnString = stringToEllipsize.substring(0, afterCharacter) + "...";
            }
            else
            {
                returnString = stringToEllipsize;
            }


        }
        catch (Exception e)
        {
            Log.e("Error in ", "Text Spannable" + e);
        }

        return returnString;
    }


    public static SpannableString spannableString(final String spannableString, String spanableText,
                                                  final OnClicked clicked)
    {
        SpannableString ss = new SpannableString(spannableString);
        String str = spannableString.toString();
        ClickableSpan clickableSpan = new SpannableTextView(false)
        {
            @Override
            public void onClick(View widget)
            {
                clicked.onClicked();
            }
        };
        ss.setSpan(clickableSpan, str.indexOf(spanableText), str
                .indexOf(spanableText) + spanableText.length(), 0);

        return ss;
    }

    public static boolean checkStringNullOrEmpty(String str)
    {
        if (str == null)
        {
            return true;
        }
        else if (str.matches(""))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public static boolean checkArrayListNullOrEmpty(ArrayList<?> arrayList)
    {
        if (arrayList == null)
        {
            return true;
        }
        else if (arrayList.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public static void setDrawableCustom(Drawable drawable, View view)
    {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
        {
            view.setBackgroundDrawable(drawable);
        }
        else
        {
            view.setBackground(drawable);
        }

    }


    public static boolean isMailPhnNo(String uname)
    {
        if (!uname.isEmpty())
        {

            if (Character.isDigit(uname.charAt(0)))
            {
                return true;
            }
        }
        return false;
    }


    public static String getVersionCode(Context context)
    {
        String versionCode = "";
        try
        {
            if (context != null)
            {
                PackageInfo pInfo = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0);
                String version = pInfo.versionName;
                int verCode = pInfo.versionCode;
                versionCode = "" + verCode;
            }

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getContactName(final String phoneNumber, Context context)
    {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri
                .encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName = "";
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                contactName = cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }

    public static int getContactIdForWhatsAppCall(String name, Context context)
    {

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data._ID},
                ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.voip.call"},
                ContactsContract.Contacts.DISPLAY_NAME);

        if (cursor.getCount() > 0)
        {
            cursor.moveToNext();
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            System.out
                    .println("9999999999999999          name  " + name + "      id    " + phoneContactID);
            return phoneContactID;
        }
        else
        {
            System.out.println("8888888888888888888          ");
            return 0;
        }
    }

    public static int getContactIdForWhatsAppVideoCall(String name, Context context)
    {
        //contactNumber = Uri.encode(contactNumber);

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data._ID},
                ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.video.call"},
                ContactsContract.Contacts.DISPLAY_NAME);
        //     int phoneContactID = new Random().nextInt();
//        Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.Data.CONTENT_URI,contactNumber),new String[] {ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data._ID}, null, null, null);
//        while(contactLookupCursor.moveToNext())
//        {
//            phoneContactID = contactLookupCursor.getInt(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.Data._ID));
//        }
//        contactLookupCursor.close();

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
//            do
//            {
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            System.out
                    .println("9999999999999999          name  " + name + "      id    " + phoneContactID);


            //}while (cursor.moveToNext());


            return phoneContactID;
        }
        else
        {
            System.out.println("8888888888888888888          ");
            return 0;
        }
    }


    public static void addContact(Context context, String DisplayName, String MobileNumber,
                                  String HomeNumber,
                                  String WorkNumber, String emailID, String company,
                                  String jobTitle)
    {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (!Utility.checkStringNullOrEmpty(DisplayName))
        {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (!Utility.checkStringNullOrEmpty(MobileNumber))
        {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //------------------------------------------------------ Home Numbers
        if (!Utility.checkStringNullOrEmpty(HomeNumber))
        {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build());
        }

        //------------------------------------------------------ Work Numbers
        if (!Utility.checkStringNullOrEmpty(WorkNumber))
        {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Email
        if (!Utility.checkStringNullOrEmpty(emailID))
        {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Organization
        if (!Utility.checkStringNullOrEmpty(company) && !Utility.checkStringNullOrEmpty(jobTitle))
        {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }

        // Asking the Contact provider to create a new contact
        try
        {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}