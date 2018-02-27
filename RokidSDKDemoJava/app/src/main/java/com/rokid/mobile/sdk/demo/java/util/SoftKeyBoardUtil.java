package com.rokid.mobile.sdk.demo.java.util;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rokid.mobile.lib.base.util.Logger;

public class SoftKeyBoardUtil {

    /**
     * hide softKeyboard
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (null == context) {
            return;
        }
        if (null == view) {
            return;
        }
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm && isKeyBoardShow(view.getRootView())) {
            Logger.d("hideSoftKeyboard is called ");
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * check softKeyboard is showing
     */
    private static boolean isKeyBoardShow(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }


}
