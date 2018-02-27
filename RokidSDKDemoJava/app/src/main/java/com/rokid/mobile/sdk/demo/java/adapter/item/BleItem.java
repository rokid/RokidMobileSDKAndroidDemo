package com.rokid.mobile.sdk.demo.java.adapter.item;

import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rokid.mobile.lib.entity.bean.bluetooth.BTDeviceBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.ui.recyclerview.item.BaseItem;
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tt on 2018/2/25.
 */

public class BleItem extends BaseItem<BTDeviceBean> {

    @BindView(R.id.item_bind_ble_name_txt)
    TextView bleNameTxt;

    @BindView(R.id.item_bind_ble_bind_txt)
    TextView bindTxt;

    @BindView(R.id.item_bind_ble_progress)
    ProgressBar progressBar;

    public BleItem(BTDeviceBean data) {
        super(data);
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_bind_ble;
    }

    @Override
    public void onReleaseViews(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        ButterKnife.bind(this, holder.getItemView());
        bleNameTxt.setText("");
        bindTxt.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSetViewsData(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        if (!TextUtils.isEmpty(getData().getName())) {
            bleNameTxt.setText(getData().getName());
        }
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showBindTxt() {
        bindTxt.setVisibility(View.VISIBLE);
    }

    public void hideBindTxt() {
        bindTxt.setVisibility(View.GONE);
    }
}
