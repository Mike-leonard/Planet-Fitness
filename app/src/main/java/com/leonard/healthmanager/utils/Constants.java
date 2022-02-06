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
    public static String Hitnext_sharedpreference = "Hitnext";





    public static String KEY_PROGRESS = "Progress";
    public static long READY_TO_GO_TIMT = 10;
    public static int REST_TIME = 30;
    public static int TOTAL_DAYS = 30;

    public static String admobAppID = "ca-app-pub-2957577039807154~2089406596";
    public static String unityAppID = "4579491";
    public static String metaAppID = "";

    public static String admobBannerId = "ca-app-pub-2957577039807154/5453936536";
    public static String admobIntersialId = "ca-app-pub-2957577039807154/4041137731";
    public static String admobNativeId = "ca-app-pub-2957577039807154/8372099131";

    public static String metaBannerId = "";
    public static String metaIntersialId = "";
    public static String metaNativeId = "";



}
