package com.rokid.mobile.sdk.demo.base

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.util.TypefaceHelper


/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/3/30
 */
class IconTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        initViews(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs)
    }

    private fun initViews(context: Context, attrs: AttributeSet?) {
        Logger.d("Init the iconTextView.")
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconTextView)

        if (typedArray.hasValue(R.styleable.IconTextView_iconColor)) {
            val iconColor = typedArray.getColor(R.styleable.IconTextView_iconColor,
                    ContextCompat.getColor(context, R.color.base_icon))
            Logger.d("This hav iconColor, so do set it, Color: " + iconColor)
            setTextColor(iconColor)
        }

        if (!typedArray.hasValue(R.styleable.IconTextView_android_textStyle)) {
            setTypeface(context)
            return
        }

        val textStyle = typedArray.getInt(R.styleable.IconTextView_android_textStyle, Typeface.NORMAL)
        setTypeface(context, textStyle)

        typedArray.recycle()
    }

    private fun setTypeface(context: Context) {
        if (!isInEditMode) {
            setTypeface(TypefaceHelper.instance!!.getIconFontTypeface(context))
        }
    }

    fun setTypeface(context: Context, style: Int) {
        if (!isInEditMode) {
            super.setTypeface(TypefaceHelper.instance!!.getIconFontTypeface(context), style)
        }
    }

    fun setIcon(text: CharSequence) {
        setText(text)
    }

    fun setIcon(@StringRes resId: Int) {
        setText(resId)
    }

    fun setIconColor(@ColorInt color: Int) {
        setTextColor(color)
    }

    fun setIconSize(size: Float) {
        textSize = size
    }

    fun setIconSize(unit: Int, size: Float) {
        setTextSize(unit, size)
    }

}
