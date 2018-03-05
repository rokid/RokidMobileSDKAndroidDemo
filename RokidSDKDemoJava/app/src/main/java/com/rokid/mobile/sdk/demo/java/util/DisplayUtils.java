package com.rokid.mobile.sdk.demo.java.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/3/5
 */
public class DisplayUtils {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null == manager) {
            return dm;
        }

        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

}
