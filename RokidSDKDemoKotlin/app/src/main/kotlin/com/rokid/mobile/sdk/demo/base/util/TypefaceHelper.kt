package com.rokid.mobile.sdk.demo.base.util

import android.content.Context
import android.graphics.Typeface

import java.util.concurrent.atomic.AtomicReference

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/8/30
 */
class TypefaceHelper {

    private val typefaceAtom = AtomicReference<Typeface>()

    fun getIconFontTypeface(context: Context): Typeface {
        if (null == typefaceAtom.get()) {
            typefaceAtom.getAndSet(Typeface.createFromAsset(context.assets,
                    "iconfont/iconfont.ttf"))
        }
        return typefaceAtom.get()
    }

    companion object {

        @Volatile
        private var mInstance: TypefaceHelper? = null

        val instance: TypefaceHelper?
            get() {
                if (null == mInstance) {
                    synchronized(TypefaceHelper::class.java) {
                        if (null == mInstance) {
                            mInstance = TypefaceHelper()
                        }
                    }
                }

                return mInstance
            }
    }

}
