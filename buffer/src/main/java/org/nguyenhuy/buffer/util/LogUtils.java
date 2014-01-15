package org.nguyenhuy.buffer.util;

import android.text.TextUtils;
import android.util.Log;
import org.nguyenhuy.buffer.Constants;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class LogUtils {
    public static void v(String msg) {
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)) {
            Log.v(Constants.LOG_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (Constants.DEBUG && !TextUtils.isEmpty(msg)) {
            Log.e(Constants.LOG_TAG, msg);
        }
    }

    public static void e(Throwable th) {
        if (Constants.DEBUG && th != null) {
            Log.e(Constants.LOG_TAG, th.getMessage(), th);
        }
    }
}
