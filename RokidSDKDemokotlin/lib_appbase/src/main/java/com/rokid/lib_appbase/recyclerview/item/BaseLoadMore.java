package com.rokid.lib_appbase.recyclerview.item;

import android.view.View;

import com.rokid.mobile.lib.base.util.Logger;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/7/9
 */
public abstract class BaseLoadMore<D> extends BaseItem<D> {

    private static final int BASE_TYPE_LOAD_MORE = 3300000;

    public BaseLoadMore(D data) {
        super(data);
    }

    @Override
    public int getViewType() {
        int viewType = BASE_TYPE_LOAD_MORE;
        Logger.d(this.toString() + " ViewType:" + viewType);
        return viewType;
    }

    @Override
    public final void onReleaseViews(BaseViewHolder holder, int sectionKey, int sectionItemPosition) {
        Logger.d("BaseLoadMore rootView set Visible GONE");
        holder.getItemView().setVisibility(View.GONE);
    }

    @Override
    public final void onSetViewsData(BaseViewHolder holder, int sectionKey, int sectionItemPosition) {
        Logger.d("BaseLoadMore rootView set Visible Visible");
        holder.getItemView().setVisibility(View.VISIBLE);
        onSetViewsData(holder);
    }

    public abstract void onSetViewsData(BaseViewHolder holder);

    public abstract void startAnim();

    public abstract void stopAnim();

}
