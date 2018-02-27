package com.rokid.mobile.sdk.demo.java.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.entity.bean.bluetooth.BTDeviceBean;
import com.rokid.mobile.lib.entity.bean.wifi.WifiBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.adapter.item.BleItem;
import com.rokid.mobile.sdk.demo.java.presenter.BindFragmentPresenter;
import com.rokid.mobile.sdk.demo.java.util.SoftKeyBoardUtil;
import com.rokid.mobile.sdk.demo.java.util.WifiEngine;
import com.rokid.mobile.ui.recyclerview.adapter.BaseRVAdapter;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class BindFragment extends BaseFragment<BindFragmentPresenter> {

    @BindView(R.id.fragment_bind_ble_name_edit)
    TextInputEditText bleNameTxt;

    @BindView(R.id.fragment_bind_scan_ble_btn)
    Button scanBleBtn;

    @BindView(R.id.fragment_bind_scan_ble_load_layer)
    LinearLayout loadLayer;

    @BindView(R.id.fragment_bind_ble_list_rv)
    RecyclerView bleRV;

    @BindView(R.id.fragment_bind_wifi_name_edit)
    TextInputEditText wifiNameTxt;

    @BindView(R.id.fragment_bind_wifi_pwd_edit)
    TextInputEditText wifiPwdTxt;

    @BindView(R.id.fragment_bind_btn)
    Button bindBtn;

    private BaseRVAdapter<BleItem> mAdapter;

    private BleItem currentItem;

    private WifiBean wifiBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind;
    }

    @Override
    protected BindFragmentPresenter initPresenter() {
        return new BindFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        bleNameTxt.setSelection(bleNameTxt.getText().length());

        mAdapter = new BaseRVAdapter<>();

        bleRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        bleRV.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        scanBleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bleNameTxt.getText())) {
                    Logger.d("ble name is empty");
                    showToastShort(getString(R.string.fragment_bind_ble_name_empty));
                    return;
                }

                SoftKeyBoardUtil.hideSoftKeyboard(getContext(), scanBleBtn);
                loadLayer.setVisibility(View.VISIBLE);
                bleRV.setVisibility(View.GONE);
                getPresenter().stopScanBle();
                mAdapter.clearAllItemView();
                getPresenter().startScanBle(bleNameTxt.getText().toString());
            }
        });

        mAdapter.setOnItemViewClickListener(new BaseRVAdapter.OnItemViewClickListener<BleItem>() {
            @Override
            public void onItemViewClick(BleItem bleItem, int i1, int i2) {
                if (bleItem == null) {
                    return;
                }

                if (bleItem.getData() == null) {
                    return;
                }

                currentItem = bleItem;
                currentItem.hideBindTxt();
                bleItem.showProgressBar();
                getPresenter().connectBle(bleItem.getData().getName());
            }
        });

        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(wifiNameTxt.getText()) &&
                        TextUtils.isEmpty(wifiPwdTxt.getText())) {
                    showToastShort(getString(R.string.fragment_bind_wifi_empty));
                    return;
                }

                SoftKeyBoardUtil.hideSoftKeyboard(getContext(), bindBtn);
                getPresenter().sendBindData(wifiNameTxt.getText().toString(),
                        wifiPwdTxt.getText().toString(),
                        wifiBean.getBssid());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initWifiInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && null != getPresenter()) {
            getPresenter().stopScanBle();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != getPresenter()) {
            getPresenter().stopScanBle();
        }
    }

    /**
     * 初始化wifi信息
     */
    private void initWifiInfo() {
        wifiBean = WifiEngine.getInstance().getConnectWifiInfo();
        if (null == wifiBean) {
            return;
        }

        if (!TextUtils.isEmpty(wifiBean.getSsid())) {
            wifiNameTxt.setText(wifiBean.getSsid());
            wifiNameTxt.setSelection(wifiBean.getSsid().length());
        }
    }

    public void setBleData(BTDeviceBean bleData) {
        hideLoadLayer();

        if (null == bleData) {
            showToastShort(getString(R.string.fragment_bind_ble_list_empty));
            return;
        }

        bleRV.setVisibility(View.VISIBLE);
        mAdapter.addItemView(new BleItem(bleData));
    }

    /**
     * 连接蓝牙成功
     */
    public void connectBleSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentItem.hideProgressBar();
                currentItem.showBindTxt();
            }
        });

        showToastShort(getString(R.string.fragment_bind_ble_connect_success));
    }

    /**
     * 连接蓝牙失败
     */
    public void connectBleFail(String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentItem.hideProgressBar();
            }
        });
        showToastShort(text);
    }

    public void hideLoadLayer() {
        loadLayer.setVisibility(View.GONE);
    }
}
