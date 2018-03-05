package com.rokid.mobile.sdk.demo.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/3/5
 */
object DisplayUtils {

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val dm = DisplayMetrics()
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getMetrics(dm)
        return dm
    }

}