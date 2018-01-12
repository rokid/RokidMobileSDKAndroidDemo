package com.rokid.lib_appbase.recyclerview.item;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rokid.lib_appbase.exception.RvAdapterException;
import com.rokid.mobile.lib.base.util.Logger;


public abstract class BaseItem<D> {

    protected BaseViewHolder mHolder;

    protected D mData;

    public BaseItem(D data) {
        this.mData = data;
    }

    public abstract int getViewType();

    /**
     * Create the View from layout.xml
     */
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getLayoutId(viewType) <= 0) {
            throw new RvAdapterException("This Layout Id is invalid.");
        }

        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false));
    }

    public abstract int getLayoutId(int viewType);

    /**
     * Bind the data to ViewHolder
     */
    public final void onBindViewHolder(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        this.mHolder = holder;


        onReleaseViews(holder, sectionKey, sectionViewPosition);

        if (null == mData) {
            Logger.e("This data is empty.");
            return;
        }
        onSetViewsData(holder, sectionKey, sectionViewPosition);
    }

    /**
     * 重置 Views 数据，避免重用时 数据冲突
     */
    public abstract void onReleaseViews(BaseViewHolder holder, int sectionKey, int sectionViewPosition);

    /**
     * 设置 Views 数据
     */
    public abstract void onSetViewsData(BaseViewHolder holder, int sectionKey, int sectionViewPosition);

    public void releaseResource() {
    }

    public D getData() {
        return mData;
    }

    public void setData(D data) {
        this.mData = data;
    }

    public BaseViewHolder getHolder() {
        return mHolder;
    }

    public Context getContext() {
        if (null == getHolder()) {
            return null;
        }

        return getHolder().getContext();
    }


    public String getString(@StringRes int resId) {
        if (null == getContext()) {
            return null;
        }

        return getContext().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        if (null == getContext()) {
            return null;
        }

        return getContext().getString(resId, formatArgs);
    }

    public String[] getStringArray(@ArrayRes int resId) {
        if (null == getContext()) {
            return null;
        }

        return getContext().getResources().getStringArray(resId);
    }

    public String getIcon(@StringRes int resId) {
        if (null == getContext()) {
            return null;
        }

        return getContext().getString(resId);
    }

    public int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    public Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    public Resources getResources() {
        if (null == getContext()) {
            return null;
        }

        return getContext().getResources();
    }




}
