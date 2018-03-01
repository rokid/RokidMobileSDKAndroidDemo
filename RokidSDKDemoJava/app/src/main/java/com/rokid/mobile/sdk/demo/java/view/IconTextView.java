package com.rokid.mobile.sdk.demo.java.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.util.TypefaceHelper;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/3/30
 */
public class IconTextView extends AppCompatTextView {

    public IconTextView(Context context) {
        super(context);
        initViews(context, null);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        Logger.d("Init the iconTextView.");
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconTextView);

        if (typedArray.hasValue(R.styleable.IconTextView_iconColor)) {
            int iconColor = typedArray.getColor(R.styleable.IconTextView_iconColor,
                    ContextCompat.getColor(getContext(), R.color.base_icon));
            Logger.d("This hav iconColor, so do set it, Color: " + iconColor);
            setTextColor(iconColor);
        }

        if (!typedArray.hasValue(R.styleable.IconTextView_android_textStyle)) {
            setTypeface(context);
            return;
        }

        int textStyle = typedArray.getInt(R.styleable.IconTextView_android_textStyle, Typeface.NORMAL);
        setTypeface(context, textStyle);

        typedArray.recycle();
    }

    private void setTypeface(Context context) {
        if (!isInEditMode()) {
            setTypeface(TypefaceHelper.getInstance().getIconFontTypeface(context));
        }
    }

    public void setTypeface(Context context, int style) {
        if (!isInEditMode()) {
            super.setTypeface(TypefaceHelper.getInstance().getIconFontTypeface(context), style);
        }
    }

    public void setIcon(CharSequence text) {
        setText(text);
    }

    public void setIcon(@StringRes int resId) {
        setText(resId);
    }

    public void setIconColor(@ColorInt int color) {
        setTextColor(color);
    }

    public void setIconSize(float size) {
        setTextSize(size);
    }

    public void setIconSize(int unit, float size) {
        setTextSize(unit, size);
    }

}
