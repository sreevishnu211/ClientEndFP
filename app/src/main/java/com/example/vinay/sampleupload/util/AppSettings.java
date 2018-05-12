package com.example.vinay.sampleupload.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by swapnil on 5/12/18.
 */

public class AppSettings {

    private static SharedPreferences prefs = null;


    private static final String APP_SHARED_PREFERENCE_NAME = "com.example.vinay.sampleupload";
    public static String PREF_LOCAL_IP = "PREF_LOCAL_IP";



    public static String getValue(Context context, String key,
                                  String defaultValue) {
        prefs = context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, 0);
        return prefs.getString(key, defaultValue);
    }

    public static void setValue(Context context, String key, String value) {
        prefs = context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value.toString());
        editor.commit();
    }

}
