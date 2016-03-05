package com.dechcaudron.xtreaming;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dechcaudron.xtreaming.controller.LogController;


public class BaseApplication extends Application
{
    private static final String TAG = LogController.makeTag(BaseApplication.class);
    private static final String SHARED_PREFERENCES_NAME = "sharedPrefs";
    private static BaseApplication singleton;

    @Override
    public void onCreate()
    {
        super.onCreate();
        singleton = this;
    }

    public static String getStringPref(String prefKey, String defaultValue)
    {
        //LogController.LOGD(TAG, "getting pref " + prefKey);
        return getSharedPreferences().getString(prefKey, defaultValue);
    }

    public static boolean setStringPrefSync(String prefKey, String value)
    {
        //LogController.LOGD(TAG, "setting pref" + prefKey + " value " + value);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        return editor.putString(prefKey, value).commit();
    }

    private static SharedPreferences getSharedPreferences()
    {
        return singleton.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


}
