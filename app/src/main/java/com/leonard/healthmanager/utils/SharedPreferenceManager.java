package com.leonard.healthmanager.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceManager {
    String TAG = SharedPreferenceManager.class.getSimpleName();
    Context context;

    public SharedPreferenceManager(Context context2) {
        this.context = context2;
    }

    public void clear_user_data() {
        Editor edit = this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).edit();
        edit.clear();
        edit.apply();
    }

    public void set_Remove_Ad(Boolean bool) {
        Editor edit = this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).edit();
        edit.putBoolean(Constants.KEY_remove_ad, bool.booleanValue());
        edit.apply();
    }

    public void set_Language(String str) {
        Editor edit = this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).edit();
        edit.putString(Constants.KEY_language, str);
        edit.apply();
    }

    public void set_Prev_Phone_Language(String str) {
        Editor edit = this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).edit();
        edit.putString(Constants.KEY_prev_phone_lang, str);
        edit.apply();
    }

    public Boolean get_Remove_Ad() {
        return Boolean.valueOf(this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).getBoolean(Constants.KEY_remove_ad, false));
    }

    public String get_Language() {
        return this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).getString(Constants.KEY_language, "en");
    }

    public String get_Prev_Phone_Language() {
        return this.context.getSharedPreferences(Constants.Hitnext_sharedpreference, 0).getString(Constants.KEY_prev_phone_lang, "en");
    }


    /*
     public static void setPref(String Key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key, value);
        editor.apply();
    }

    public static Boolean getPref(String Key, boolean value) {
        return sharedPreferences.getBoolean(Key, value);
    }


    public static void setPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value);
        editor.apply();
    }
    public static String getPref(Context context, String key, String value) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, value);
    }


    public static void setPref(Context context, String key, Integer value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value);
        editor.apply();
    }

    public static Integer getPref(Context context, String key, Integer value) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, value);
    }
*/
}
