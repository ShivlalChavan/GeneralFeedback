package com.example.feedbackapp.Common;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Param3 on 10/1/2016.
 */
public class CalendarUtil
{

    public static String convertDate(String d_date)
    {
        String result = null;
        try
        {
            String dateStr = "Mon Jun 18 00:00:00 IST 2012";
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date date = (Date) formatter.parse(d_date);
            System.out.println(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            result = format.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertDate2(String d_date)
    {
        String result = null;
        try
        {
            String dateStr = "Mon Jun 18 00:00:00 IST 2012";
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date date = (Date) formatter.parse(d_date);
            System.out.println(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = format.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertDate1(String d_date)
    {
        String result = null;
        try
        {
            DateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
            Date date = (Date) formatter.parse(d_date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            result = format.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertDate3(String d_date)
    {
        String result = null;
        try
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date) formatter.parse(d_date);
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yy");
            result = format.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertDate4(String d_date)
    {
        String result = null;
        try
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date) formatter.parse(d_date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            result = format.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean dateEqual(String start_date, String end_date)
    {
        boolean isValid = false;
        if (!Utility.checkStringNullOrEmpty(start_date) && !Utility
                .checkStringNullOrEmpty(end_date))
        {
            try
            {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Date date1 = sdf.parse(start_date);
                Date date2 = sdf.parse(end_date);
                System.out.println(sdf.format(date1));
                System.out.println(sdf.format(date2));

                if (date1.after(date2))
                {
                    System.out.println("Date1 is after Date2");
                    isValid = false;
                }
                else if (date1.equals(date2))
                {
                    isValid = true;
                }
                else if (date1.before(date2))
                {
                    isValid = false;
                }

           /* if(date1.before(date2)){
                System.out.println("Date1 is before Date2");
            }

            if(date1.equals(date2)){
                System.out.println("Date1 is equal Date2");
            }*/

            }
            catch (ParseException ex)
            {
                Log.e("Error in", "date parsing after" + ex);
            }
        }
        return isValid;
    }

    public static boolean dateAfter(String start_date, String end_date)
    {
        boolean isValid = false;
        if (!Utility.checkStringNullOrEmpty(start_date) && !Utility
                .checkStringNullOrEmpty(end_date))
        {
            try
            {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Date date1 = sdf.parse(start_date);
                Date date2 = sdf.parse(end_date);
                System.out.println(sdf.format(date1));
                System.out.println(sdf.format(date2));

                if (date1.after(date2))
                {
                    System.out.println("Date1 is after Date2");
                    isValid = true;
                }
                else if (date1.equals(date2))
                {
                    isValid = true;
                }
                else if (date1.before(date2))
                {
                    isValid = false;
                }

           /* if(date1.before(date2)){
                System.out.println("Date1 is before Date2");
            }

            if(date1.equals(date2)){
                System.out.println("Date1 is equal Date2");
            }*/

            }
            catch (ParseException ex)
            {
                Log.e("Error in", "date parsing after" + ex);
            }
        }
        return isValid;
    }

    public static boolean dateBefore(String start_date, String end_date)
    {
        boolean isValid = false;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(start_date);
            Date date2 = sdf.parse(end_date);
            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.before(date2))
            {
                System.out.println("Date1 is after Date2");
                isValid = true;
            }
            else if (date1.equals(date2))
            {
                System.out.println("Date1 & Date2 Match");
                isValid = true;
            }
            else
            {
                System.out.println("Date1 & Date2 are not match");
                isValid = false;
            }

           /* if(date1.before(date2)){
                System.out.println("Date1 is before Date2");
            }

            if(date1.equals(date2)){
                System.out.println("Date1 is equal Date2");
            }*/

        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        return isValid;
    }


    public static long dateTimeValidate(String start_date, String end_date)
    {

        long diffHours = 0;
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start_date);
            Date d2 = format.parse(end_date);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return diffHours;

    }

    public static String datTimeAfter(int hours)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, hours);
        return dateFormat.format(cal.getTime());
    }

    public static boolean dateAfter1(String start_date, String end_date)
    {
        boolean isValid = false;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(start_date);
            Date date2 = sdf.parse(end_date);
            if (date1.after(date2))
            {
                System.out.println("Date1 is after Date2");
                isValid = true;
            }

           /* if(date1.before(date2)){
                System.out.println("Date1 is before Date2");
            }

            if(date1.equals(date2)){
                System.out.println("Date1 is equal Date2");
            }*/

        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        return isValid;
    }

    public static String convert_time_span_date(String current_date, int days)
    {
        String date = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(current_date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            cal.add(Calendar.DATE, days);
            Date dateBefore7Days = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(dateBefore7Days);
            System.out.println("Date converted to String: " + date);
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        return date;
    }

    public static String convert_time_span_date1(String current_date, int days)
    {
        String date = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(current_date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            cal.add(Calendar.DATE, days);
            Date dateBefore7Days = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(dateBefore7Days);
            System.out.println("Date converted to String: " + date);
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        return date;
    }

    public static int getCountDays(String start_date, String end_date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        try
        {
            Date start_dateConvertDate = sdf.parse(start_date);
            calendar1.setTime(start_dateConvertDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        try
        {
            Date end_dateConvertDate = sdf.parse(end_date);
            calendar2.setTime(end_dateConvertDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        long miliSecondForDate1 = calendar1.getTimeInMillis();
        long miliSecondForDate2 = calendar2.getTimeInMillis();

        // Calculate the difference in millisecond between two dates
        long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
        long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);

        return (int) diffInDays;
    }

    public static String getTimeSpanDate(int position)
    {

        String current_date = null, current_date_time = null;
        try
        {
            Calendar c = Calendar.getInstance();
            final Date date = c.getTime();
            final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            current_date_time = ft.format(date);
            final SimpleDateFormat ftt = new SimpleDateFormat("yyyy-MM-dd");
            current_date = ftt.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String date = null;

        switch (position)
        {

            case 0:
                date = convert_time_span_date(current_date_time, 0);
                break;

            case 1:
                date = datTimeAfter(-2);
                break;

            case 2:
                date = datTimeAfter(-8);
                break;

            case 3:
                date = convert_time_span_date(current_date_time, -1);
                break;

            case 4:
                date = convert_time_span_date(current_date_time, -2);
                break;

            case 5:
                date = convert_time_span_date(current_date_time, -6);
                break;

            default:
                date = null;
                break;

        }

        return date;
    }

}
