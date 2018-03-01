package com.rokid.mobile.sdk.demo.java.device;

import com.rokid.mobile.lib.entity.bean.device.RKDevice;
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback;
import com.rokid.mobile.lib.xbase.device.callback.IUnbindDeviceCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

import java.util.List;

/**
 * Created by tt on 2018/2/24.
 */

public class DeviceFragmentPresenter extends BaseFragmentPresenter<DeviceListFragment> {
    public DeviceFragmentPresenter(DeviceListFragment fragment) {
        super(fragment);
    }

    public void getDeviceList() {
        RokidMobileSDK.device.getDeviceList(new IGetDeviceListCallback() {
            @Override
            public void onGetDeviceListSucceed(List<RKDevice> list) {
                getFragment().setDeviceListData(list);
            }

            @Override
            public void onGetDeviceListFailed(String s, String s1) {
                getFragment().showToast(getFragment().getString(R.string.fragment_device_list_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
            }
        });
    }

    public void unbindDevice(String deviceId) {
        RokidMobileSDK.device.unbindDevice(deviceId, new IUnbindDeviceCallback() {
            @Override
            public void onUnbindDeviceSucceed() {
                getFragment().showToast(getFragment().getString(R.string.fragment_device_unbind_success));
                getDeviceList();
            }

            @Override
            public void onUnbindDeviceFailed(String s, String s1) {
                getFragment().showToast(getFragment().getString(R.string.fragment_device_unbind_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
            }
        });
    }
}
