package com.example.feedbackapp.Common;

import java.util.Locale;

public class Util
{
    public static boolean isZhCN() {
        Locale locale = Locale.getDefault();
        return "zh".equalsIgnoreCase(locale.getLanguage()) && "cn".equalsIgnoreCase(locale.getCountry());
    }
}
