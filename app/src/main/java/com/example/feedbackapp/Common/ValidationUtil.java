package com.example.feedbackapp.Common;

import android.content.Context;
import android.util.Patterns;


import com.example.feedbackapp.Interface.ValidationInterface;
import com.example.feedbackapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Param3 on 8/31/2016.
 */
public class ValidationUtil
{

    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public static boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void userNameValidation(Context context, String usernameValue,
                                          final ValidationInterface target)
    {
        if (usernameValue.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.name_empty));

        }
        else if (usernameValue.length() < 3)
        {
            target.onError(context.getResources().getString(R.string.name_length));

        }
        else
        {
            target.onError("");
        }

    }

    public static void websiteLinkValidaton(Context context, String websiteLink,
                                            final ValidationInterface target)
    {
        if (websiteLink.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.web_site_name_empty));

        }
        else if (!Patterns.WEB_URL.matcher(websiteLink).matches())
        {
            target.onError(context.getResources().getString(R.string.web_site_name_not_valid));

        }
        else
        {
            target.onError("");
        }

    }

    public static boolean isMailPhnNo(String uname)
    {
        if (Character.isDigit(uname.charAt(0)))
        {
            return true;
        }
        return false;
    }

    public static void mobileNumberValidation(Context context, String mobileNumberValue,
                                              final ValidationInterface target)
    {
        if (mobileNumberValue.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.mobile_number_empty));

        }
        else if (!mobileNumberValue.matches("\\d{10}"))
        {
            target.onError(context.getResources().getString(R.string.mobile_number_length));
        }
        else
        {
            target.onError("");
        }

    }

    public static void panCardValidation(Context context, String padCardNumber,
                                         final ValidationInterface target)
    {
        if (padCardNumber.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.pan_number_empty));

        }
        else if (!padCardNumber.toUpperCase().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}"))
        {
            target.onError(context.getResources().getString(R.string.pan_number_not_valid));
        }
        else
        {
            target.onError("");
        }

    }

    public static void pincodeValidation(Context context, String pinCode,
                                         final ValidationInterface target)
    {
        if (pinCode.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.pin_code_empty));

        }
        else if (!pinCode.matches("^[1-9][0-9]{5}$"))
        {
            target.onError(context.getResources().getString(R.string.pin_code_not_valid));
        }
        else
        {
            target.onError("");
        }

    }

    public static void validateAadharNumber(Context context, String aadharNumber,
                                            final ValidationInterface target)
    {
        if (aadharNumber.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.adhar_number_empty));

        }
        else
        {
            Pattern aadharPattern = Pattern.compile("\\d{12}");
            boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
            if (isValidAadhar)
            {
                target.onError("");
            }
            else
            {
                target.onError(context.getResources().getString(R.string.adhar_number_not_valid));
            }
        }
    }


    public static void passwordValidation(Context context, String passwordValue,
                                          final ValidationInterface target)
    {
        if (passwordValue.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.password_empty));

        }
        else if (passwordValue.length() < 5 || passwordValue.length() > 15)
        {
            target.onError(context.getResources().getString(R.string.password_length));

        }

    }

    public static void emailValidation(Context context, String emailIDValue,
                                       final ValidationInterface target)
    {
        if (emailIDValue.isEmpty())
        {
            target.onError(context.getResources().getString(R.string.email_empty));
        }
        else if (!ValidationUtil.emailValidator(emailIDValue))
        {
            target.onError(context.getResources().getString(R.string.email_not_valid));
        }

    }
}
