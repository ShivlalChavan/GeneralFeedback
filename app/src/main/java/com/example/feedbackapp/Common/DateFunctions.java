package com.example.feedbackapp.Common;

import android.util.Log;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateFunctions
{

    // Last Months Name List From Current Month
    public static ArrayList<String> getLastMonthsNameListFromCurrentMonth(int lastMonthsLimit)
    {
        // TODO Auto-generated method stub

        ArrayList<String> list = new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");

        for (int i = lastMonthsLimit; i >= 0; i--)
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);

            list.add("" + df.format(cal.getTime()));

            Log.e("Dates ", "" + df.format(cal.getTime()));

        }
        return list;
    }

    // Convert Timestamp into Your provided Pattern
    public static String convertTimeStampIntoYourPattern(String timeStampDate, String pattern)
    {
        BigInteger time = new BigInteger(timeStampDate);
        Date date = new Date(time.longValue());
        DateFormat format = new SimpleDateFormat(pattern);

        return format.format(date);
    }

    // Get Month Name from Month Id
    public static String getMonthNameFromId(int i)
    {
        // TODO Auto-generated method stub

        /*Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MONTH, i-1);

        Date date = new Date(cal.getTimeInMillis());
        DateFormat format = new SimpleDateFormat("MMM");

        return format.format(date);*/
        i--;
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (i >= 0 && i <= 11)
        {
            month = months[i];
        }

        Date date = null;
        try
        {
            date = new SimpleDateFormat("MMMM").parse(month);
        }
        catch (ParseException e)
        {
            Log.e("Error", "" + e);
        }
        DateFormat format = new SimpleDateFormat("MMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return format.format(date);

    }

    //List of Selected Months All Dates
    public static ArrayList<String> getSelectedMonthAllDatesList(int month,
                                                                 Date fiscalYearStartDate,
                                                                 Date fiscalYearEndDate)
    {
        ArrayList<String> list = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 1);    //Set 1 to the first day of month
        cal.set(Calendar.MONTH, month - 1);     // Reduce month count because Jan is at position 0
        //cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        Date calDate = cal.getTime();     // Calculated date

        System.out.println("" + calDate);

        Date todaysDate = new Date();    //Todays Date
        todaysDate.setHours(0);
        todaysDate.setMinutes(0);
        todaysDate.setSeconds(0);

        // Check calculated date between financial year
        if (fiscalYearStartDate.before(calDate) && fiscalYearEndDate.after(calDate))
        {
            // In financial year

            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM");

            for (int i = 0; i < maxDay; i++)
            {
                cal.set(Calendar.DAY_OF_MONTH, i + 1);
                list.add(df.format(cal.getTime()));
            }


        }
        else
        {

            // Non In financial year
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.MONTH, month - 1);
            cal1.set(Calendar.YEAR, -1);        // Set last year date (if Now 2018 then set to 2017)
            cal1.set(Calendar.DAY_OF_MONTH, 1);

            int maxDay = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM");

            for (int i = 0; i < maxDay; i++)
            {
                cal1.set(Calendar.DAY_OF_MONTH, i + 1);
                list.add(df.format(cal1.getTime()));
            }


        }


        return list;
    }


    // Get TimeStamp of Last Months end date
    public static long getTimestampOfLastMonthEndDate()
    {

        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH);

        cal.set(Calendar.MONTH, month - 1);     // Reduce month count because Jan is at position 0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));  // Calculate last day/Date of selected month

        return cal.getTimeInMillis();
    }

    public static String getCurrentDayTimestamp()
    {
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return String.valueOf(cal.getTimeInMillis());
    }

    public static Long addDaysToCurrentDate(Long currrentDate, Integer daysToAdd)
    {
        Date date = null;
        Long dateToLong = null;
        try
        {
            Calendar calender = new GregorianCalendar();
            calender.add(Calendar.DATE, daysToAdd);
            date = calender.getTime();
            dateToLong = date.getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return dateToLong;

    }

    public static int getFiscalYearOfDate(long dateTime)
    {
        try
        {
            int preOrPostYear = 0;
            Calendar calender = new GregorianCalendar();
            calender.setTimeInMillis(dateTime);
            int month = calender.get(Calendar.MONTH) + 1;
            if (month >= 1 && month <= 3)
            {
                preOrPostYear = calender.get(Calendar.YEAR) - 1;
                return Integer.parseInt(preOrPostYear + "" + calender.get(Calendar.YEAR));
            }
            else
            {
                preOrPostYear = calender.get(Calendar.YEAR) + 1;
                return Integer.parseInt(calender.get(Calendar.YEAR) + "" + preOrPostYear);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getStartFiscalDate(long dateTime)
    {
        Calendar cal = null;
        try
        {
            int fiscalYear = getFiscalYearOfDate(dateTime);
            int year = Integer.parseInt(String.valueOf(fiscalYear).substring(0, 4));
            cal = new GregorianCalendar();
            cal.set(Calendar.DATE, 1);
            cal.set(Calendar.MONTH, Calendar.APRIL);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cal.getTimeInMillis();
    }



    public static long getEndFiscalDate(long dateTime)
    {
        Calendar cal = null;
        try
        {
            int fiscalYear = getFiscalYearOfDate(dateTime);
            int year = Integer.parseInt(String.valueOf(fiscalYear).substring(4, 8));
            cal = new GregorianCalendar();
            cal.set(Calendar.DATE, 31);
            cal.set(Calendar.MONTH, Calendar.MARCH);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cal.getTimeInMillis();
    }

    public static Date addDays(Date date, int days)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }


    public static String longDateToStringDate(Long inputDate)
    {
        try
        {
            String date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date = dateFormat.format(new Date(inputDate));
            return date;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static long stringDateToLongDate(String dateFormate, String date)
    {
        long milliseconds = 0;
        SimpleDateFormat f = new SimpleDateFormat(dateFormate);
        try
        {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        }
        catch (ParseException e)
        {
            Log.e("Error in Long", " to String date conversion" + e);
        }

        return milliseconds;
    }

    public static String longDateToStringDate(Long inputDate, String pattern)
    {
        try
        {
            String date;
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.format(new Date(inputDate));
            return date;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


    //Get last month/30 th day timestamp
    public static String getLastMonthDayTimeStamp()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -30);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return String.valueOf(cal.getTimeInMillis());
    }


    //Get timestamp of before days from current day
    public static String getTimeStampBeforeDaysFromCurrentDay(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return String.valueOf(cal.getTimeInMillis());
    }


    //Get timestamp of before months from current day
    public static String getTimeStampBeforeMonthsFromCurrentDay(int month)
    {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -month);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return String.valueOf(cal.getTimeInMillis());
    }


    //Get timestamp of before year from current day
    public static String getTimeStampBeforeYearFromCurrentDay(int year)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -year);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return String.valueOf(cal.getTimeInMillis());
    }

    // Convert Timestamp into dd MMM format
    public static String convertTimeStampIntoddMMM(String timeStampDate, String timeStamp)
    {
        BigInteger time = new BigInteger(timeStampDate);
        Date date = new Date(time.longValue());
        DateFormat format = new SimpleDateFormat(timeStamp);

        return format.format(date);
    }


    //List Of LastDays From CurrentDay
    public static ArrayList<String> getListOfLastDaysFromCurrentDay(int lastDaysLimit)
    {
        // TODO Auto-generated method stub

        ArrayList<String> list = new ArrayList<String>();

        for (int i = lastDaysLimit; i >= 0; i--)
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM");

            list.add(df.format(cal.getTime()));

        }
        return list;
    }


    // Get first date of selected month
    public static long getFirstDateOfMonth(int month, Date fiscalYearStartDate,
                                           Date fiscalYearEndDate)
    {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 1);    //Set 1 to the first day of month
        cal.set(Calendar.MONTH, month - 1);     // Reduce month count because Jan is at position 0
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date date = cal.getTime();
        Date todaysDate = new Date();

        //Check calculated date between financial year
        if (fiscalYearStartDate.before(date) && fiscalYearEndDate.after(date))
        {
            // Check Todays date is comes before of the calculated date
            if (todaysDate.before(date))
            {
                Calendar cal1 = Calendar.getInstance();

                cal1.set(Calendar.DAY_OF_MONTH, 1);
                cal1.set(Calendar.MONTH, month - 1);
                cal1.add(Calendar.YEAR, -1);   // Get last year by reducing 1 yr from current yr
                cal1.set(Calendar.HOUR, 0);
                cal1.set(Calendar.MINUTE, 0);
                cal1.set(Calendar.SECOND, 0);
                cal1.set(Calendar.MILLISECOND, 0);

                return cal1.getTimeInMillis();
            }
            else
            {
                return date.getTime();
            }
        }
        else
        {

            // Check Todays date is comes before of the calculated date
            if (todaysDate.before(date))
            {
                Calendar cal1 = Calendar.getInstance();

                cal1.set(Calendar.DAY_OF_MONTH, 1);
                cal1.set(Calendar.MONTH, month - 1);
                cal1.add(Calendar.YEAR, -1);
                cal1.set(Calendar.HOUR, 0);
                cal1.set(Calendar.MINUTE, 0);
                cal1.set(Calendar.SECOND, 0);
                cal1.set(Calendar.MILLISECOND, 0);

                return cal1.getTimeInMillis();
            }
            else
            {
                return 0;
            }


        }

    }

    // Get last date of selected month
    public static long getLastDateOfMonth(long date, Date fiscalYearStartDate,
                                          Date fiscalYearEndDate)
    {

        Date d = new Date(date);   //Convert long date into "Date format"

        Calendar cal = Calendar.getInstance();

        cal.setTime(d);               // Set selected months 1st date to calendar
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));  // Calculate last day/Date of selected month

        Date calDate = cal.getTime();     // Calculated date
        Date todaysDate = new Date();    //Todays Date

        // Check calculated date between financial year
        if (fiscalYearStartDate.before(calDate) && fiscalYearEndDate.after(calDate))
        {
            // Check Todays date is comes before of the calculated date
            if (todaysDate.before(calDate))
            {
                return todaysDate.getTime();   // Return Todays date as a end date

            }
            else
            {

                return cal.getTimeInMillis();  // Return Calculated date as a end date
            }

        }
        else
        {

            //
        }
        return 0;
    }


    // Current financial year start date = 1-Apr-2017
    public static Date financialYearStartDate()
    {
        Calendar cal = Calendar.getInstance();
        // cal.setTime(new );
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 3);
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }


    // Current financial year End date = 31-Mar-2018
    public static Date financialYearEndDate()
    {
        Calendar cal = Calendar.getInstance();
        // cal.setTime(new );
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH, 2);
        //cal.add(Calendar.YEAR,-1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
}
