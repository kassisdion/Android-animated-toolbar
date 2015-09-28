package com.kassisdion.utils;

import com.kassisdion.lib.BuildConfig;

import android.util.Log;

public final class LogHelper {
    static final boolean IS_ENABLE = BuildConfig.DEBUG;

    private LogHelper() {
    }

    public static void v(final String tag, final String msg) {
        if (IS_ENABLE)
            Log.v(tag, msg);
    }

    public static void d(final String tag, final String msg) {
        if (IS_ENABLE)
            Log.d(tag, msg);
    }

    public static void i(final String tag, final String msg) {
        if (IS_ENABLE)
            Log.i(tag, msg);
    }

    public static void w(final String tag, final String msg) {
        if (IS_ENABLE)
            Log.w(tag, msg);
    }

    public static void e(final String tag, final String msg) {
        if (IS_ENABLE)
            Log.e(tag, msg);
    }
}
