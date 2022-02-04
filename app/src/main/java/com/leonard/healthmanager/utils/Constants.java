package com.leonard.healthmanager.utils;

import android.content.SharedPreferences;

public class Constants {

    public static SharedPreferences prefAdHourControl, appInstallPref;
    public static SharedPreferences.Editor adHourEditor, appInstallEditor;

    public static SharedPreferences prefHourBanAds, prefHourIntAds, prefHourNatAds,
            prefDailyBanAds, prefDailyIntAds, prefDailyNatAds,
            prefDailyAdClicked;
    public static SharedPreferences.Editor hourBanEditor, hourIntEditor, hourNatEditor,
            dailyBanEditor, dailyIntEditor, dailyNatEditor,
            dailyAdClickedEditor;
    public static int perHoursBannerAds, dailyMaxBannerAds;
    public static int perHoursIntersialAds, dailyMaxIntersialAds, dailyAdClicked;
    public static int perHoursNativeAds, dailyMaxNativeAds;

    public static String KEY_language = "language";
    public static String KEY_prev_phone_lang = "prev_phone_lang";
    public static String KEY_remove_ad = "remove_ad";
    public static String Loopbots_sharedpreference = "Loopbots";





    public static String KEY_PROGRESS = "Progress";
    public static long READY_TO_GO_TIMT = 10;
    public static int REST_TIME = 30;
    public static int TOTAL_DAYS = 30;



}
