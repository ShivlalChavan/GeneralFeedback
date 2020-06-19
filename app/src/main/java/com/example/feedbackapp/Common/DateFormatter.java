package com.example.feedbackapp.Common;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by admin on 12/4/2015.
 */
public class DateFormatter
{

    public static SimpleDateFormat dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat EEEE = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    public static SimpleDateFormat MMMM_dd = new SimpleDateFormat("MMMM d", Locale.ENGLISH);
    public static SimpleDateFormat MMMM_dd_yyyy = new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH);
    public static SimpleDateFormat df_yyyy_MM_dd_hh_mm_ss = new SimpleDateFormat(getISO8601_4_Regex(), Locale.ENGLISH);
    public static SimpleDateFormat df_yyyy_MM = new SimpleDateFormat(getDateFormat_4_Regex(), Locale.ENGLISH);
    public static SimpleDateFormat dd_MM_YYYY = new SimpleDateFormat(getDateFormatDDMMYY_Regex(), Locale.ENGLISH);

    public static String calculateDay(String measureDate, String dateFormatter)
    {
        String day = "Today";
        Date date = new Date();
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatter);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = dateFormat.parse(measureDate);
            SimpleDateFormat localDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String tempDate = localDateFormat.format(date);
            date = localDateFormat.parse(tempDate);
        }
        catch (Exception e)
        {
            Log.e("Error in Date Format", "" + e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar todayCalendar = Calendar.getInstance();
        long diff = todayCalendar.getTimeInMillis() - calendar.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        if (days == 0)
        {
            day = "Today";
        }
        else if (days == 1)
        {
            day = "Yesterday";
        }
        else
        {
            day = DateFormatter.EEEE.format(calendar.getTime());
        }
        return day;
    }

    public static String calculateDayFormat(String measureDate)
    {
        String day = "Today";
        Date date = new Date();
        try
        {
            date = DateFormatter.df_yyyy_MM.parse(measureDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String parseDate = simpleDateFormat.format(date);
            date = simpleDateFormat.parse(parseDate);
        }
        catch (Exception e)
        {
            Log.e("Error", "in Date Formation", e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar todayCalendar = Calendar.getInstance();
        long diff = todayCalendar.getTimeInMillis() - calendar.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        if (days == 0)
        {
            day = "Today";
        }
        else if (days == 1)
        {
            day = "Yesterday";
        }
        else
        {
            day = DateFormatter.EEEE.format(calendar.getTime());
        }
        return day;
    }


    public static String getISO8601_4_Regex()
    {
        return CommonVars.DATE_FORMAT_UTCZ;
    }

    public static String getDateFormat_4_Regex()
    {
        return CommonVars.DATE_FORMAT;
    }

    public static String getDateFormatDDMMYY_Regex()
    {
        return CommonVars.DATE_FORMAT_DDMMYY;
    }


    public static String getUTCTimeString(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));//UTC
        calendar.setTime(date);
        Date time = calendar.getTime();
        String dateAsString = df_yyyy_MM_dd_hh_mm_ss.format(time);

        DateFormat formatterUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone
        Log.e("Error", "" + formatterUTC.format(date)); // out


        return formatterUTC.format(date);


    }
}
