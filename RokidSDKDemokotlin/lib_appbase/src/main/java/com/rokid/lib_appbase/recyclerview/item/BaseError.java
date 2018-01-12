package com.rokid.lib_appbase.recyclerview.item;

import com.rokid.mobile.lib.base.util.Logger;

import java.util.Random;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/7/9
 */
public abstract class BaseError<D> extends BaseItem<D> {

    private static final int BASE_TYPE_ERROR = 3200000 + new Random().nextInt(1000);

    public BaseError(D data) {
        super(data);
    }

    @Override
    public int getViewType() {
        int viewType = BASE_TYPE_ERROR;
        Logger.d(this.toString() + " ViewType:" + viewType);
        return viewType;
    }

    @Override
    public final void onReleaseViews(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
    }

    @Override
    public final void onSetViewsData(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        onSetViewsData(holder);
    }

    public abstract void onSetViewsData(BaseViewHolder holder);

}
