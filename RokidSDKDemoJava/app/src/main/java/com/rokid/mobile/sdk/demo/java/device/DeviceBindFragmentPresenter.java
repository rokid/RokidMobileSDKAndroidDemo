package com.rokid.mobile.sdk.demo.java.device;

import android.util.Log;

import com.rokid.mobile.lib.entity.bean.bluetooth.BTDeviceBean;
import com.rokid.mobile.lib.entity.bean.bluetooth.DeviceBinderData;
import com.rokid.mobile.lib.xbase.binder.bluetooth.callBack.IBTConnectCallBack;
import com.rokid.mobile.lib.xbase.binder.bluetooth.callBack.IBTScanCallBack;
import com.rokid.mobile.lib.xbase.binder.bluetooth.callBack.IBTSendCallBack;
import com.rokid.mobile.lib.xbase.binder.bluetooth.callBack.IBinderCallBack;
import com.rokid.mobile.lib.xbase.binder.bluetooth.exception.BleException;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.DemoMainActivity;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

/**
 * Created by tt on 2018/2/24.
 */

public class DeviceBindFragmentPresenter extends BaseFragmentPresenter<DeviceBindFragment> {

    private static final String TAG = DeviceBindFragmentPresenter.class.getSimpleName();

    public DeviceBindFragmentPresenter(DeviceBindFragment fragment) {
        super(fragment);
    }

    /**
     * stop scanning the ble
     */
    public void stopScanBle() {
        RokidMobileSDK.binder.stopBTScanAndClearList();
    }

    /**
     * start to scan ble according to the bleMark
     */
    public void startScanBle(String bleMark) {
        RokidMobileSDK.binder.startBTScan(bleMark, new IBTScanCallBack() {
            @Override
            public void onNewDevicesFound(BTDeviceBean btDeviceBean) {
                getFragment().setBleData(btDeviceBean);
            }
        });
    }

    /**
     * connect to the chosen ble
     */
    public void connectBle(String bleName) {
        RokidMobileSDK.binder.connectBT(bleName, new IBTConnectCallBack() {
            @Override
            public void onConnectSucceed(BTDeviceBean btDeviceBean) {
                getFragment().connectBleSuccess();
            }

            @Override
            public void onConnectFailed(BTDeviceBean btDeviceBean, BleException e) {
                Log.d(TAG, "connectBle failed, btDeviceBean = " + btDeviceBean + " , errorMsg =  " + e);
                getFragment().connectBleFail(getFragment().getString(R.string.fragment_bind_ble_connect_fail)
                        + "\n btDeviceBean = " + btDeviceBean.toString() + " , errorMsg =  " + e);
            }
        });
    }

    public void sendBindData(String ssid, String pwd, String bssid) {

        DeviceBinderData binderData = DeviceBinderData.newBuilder()
                .userId(DemoMainActivity.UID)
                .wifiPwd(pwd)
                .wifiSsid(ssid)
                .wifiBssid(bssid)
                .build();

        RokidMobileSDK.binder.sendBTBinderData(binderData, new IBinderCallBack() {
            @Override
            public void onSendSucceed(BTDeviceBean btDeviceBean) {
                getFragment().showToastShort(getFragment().getString(R.string.fragment_bind_send_success));
            }

            @Override
            public void onSendFailed(BTDeviceBean btDeviceBean, BleException bleException) {
                Log.d(TAG, "sendBindData failed, btDeviceBean = " + btDeviceBean);
                getFragment().showToastShort(getFragment().getString(R.string.fragment_bind_send_fail)
                        + "\n btDeviceBean = " + btDeviceBean.toString());
            }
        });
    }
}
