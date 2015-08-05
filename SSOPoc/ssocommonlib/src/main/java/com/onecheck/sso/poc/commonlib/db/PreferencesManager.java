package com.onecheck.sso.poc.commonlib.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by deepak on 4/8/14.
 */
public class PreferencesManager {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor prefsEditor;
    private static PreferencesManager preferenceManager;

    //Preference value keys.
    public static String LOGIN_DATA_PROV_PACKAGE;

    private PreferencesManager(Context context) {
        preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = preferences.edit();
    }

    public static PreferencesManager getInstance(Context context) {
        if (preferenceManager == null) {
            preferenceManager = new PreferencesManager(context);
        }
        prefsEditor = preferences.edit();
        return preferenceManager;
    }

    public boolean writeToPrefs(String key, String val) {
        if (val == null) val = "";

        prefsEditor.putString(key, val);
        return prefsEditor.commit();
    }

    public boolean writeToPrefs(String key, long val) {
        prefsEditor.putLong(key, val);
        return prefsEditor.commit();
    }

    public boolean writeToPrefs(String key, int val) {
        prefsEditor.putInt(key, val);
        return prefsEditor.commit();
    }

    public boolean writeToPrefs(String key, boolean val) {
        prefsEditor.putBoolean(key, val);
        return prefsEditor.commit();
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public boolean getBool(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean getBool(String key,boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public long getLong(String key) {
        return preferences.getLong(key, -1L);
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

}
