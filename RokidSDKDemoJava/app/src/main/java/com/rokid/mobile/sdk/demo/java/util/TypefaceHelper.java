package com.rokid.mobile.sdk.demo.java.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/8/30
 */
public class TypefaceHelper {

    private static volatile TypefaceHelper mInstance;
    private final AtomicReference<Typeface> typefaceAtom = new AtomicReference<>();

    public static TypefaceHelper getInstance() {
        if (null == mInstance) {
            synchronized (TypefaceHelper.class) {
                if (null == mInstance) {
                    mInstance = new TypefaceHelper();
                }
            }
        }

        return mInstance;
    }

    public Typeface getIconFontTypeface(Context context) {
        if (null == typefaceAtom.get()) {
            typefaceAtom.getAndSet(Typeface.createFromAsset(context.getAssets(),
                    "iconfont/iconfont.ttf"));
        }
        return typefaceAtom.get();
    }

}
