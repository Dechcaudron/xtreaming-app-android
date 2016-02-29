package com.dechcaudron.xtreaming;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class BaseApplication extends Application {

    private static final String SHARED_PREFERENCES_NAME = "sharedPrefs";
    private static BaseApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

    }

    public static String readStringPref(String prefKey, String defaultValue) {
        return getSharedPreferences().getString(prefKey, defaultValue);
    }

    private static SharedPreferences getSharedPreferences() {
        return singleton.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


}
