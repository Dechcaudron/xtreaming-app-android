package com.dechcaudron.xtreaming.controller;

import android.util.Log;

public abstract class LogController {

    public static String makeTag(Class<?> cls) {
        return cls.getName();
    }

    public static void LOGD(String TAG, String message) {
        Log.d(TAG, message);
    }

    public static void LOGI(String TAG, String message) {
        Log.i(TAG, message);
    }

    public static void LOGI(String TAG, String message, Throwable e) {
        Log.i(TAG, message, e);
    }

    public static void LOGW(String TAG, String message) {
        Log.w(TAG, message);
    }

    public static void LOGW(String TAG, String message, Throwable e) {
        Log.w(TAG, message, e);
    }

    public static void LOGE(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static void LOGE(String TAG, String message, Throwable e) {
        Log.e(TAG, message, e);
    }
}
