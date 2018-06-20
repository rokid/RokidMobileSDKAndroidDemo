package com.rokid.mobile.sdk.demo.java.skill;

import com.rokid.mobile.lib.base.util.CollectionUtils;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.bean.SDKDevice;
import com.rokid.mobile.sdk.callback.SDKGetDeviceListCallback;
import com.rokid.mobile.sdk.callback.SDKGetRemindListCallback;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

import java.util.List;

/**
 * Created by tt on 2018/2/24.
 */

public class SkillRemindFragmentPresenter extends BaseFragmentPresenter<SkillRemindFragment> {
    public SkillRemindFragmentPresenter(SkillRemindFragment fragment) {
        super(fragment);
    }

    @Override
    public void onLoadData() {
        super.onLoadData();
        getDeviceList();
    }

    private void getDeviceList() {
        RokidMobileSDK.device.getDeviceList(new SDKGetDeviceListCallback() {
            @Override
            public void onGetDeviceListSucceed(List<SDKDevice> list) {
                if (CollectionUtils.isEmpty(list)) {
                    getFragment().showToastShort(getFragment().getString(R.string.fragment_device_list_empty));
                    return;
                }

                getFragment().setDeviceListInfo(list);
            }

            @Override
            public void onGetDeviceListFailed(String s, String s1) {
                getFragment().showToastShort(getFragment().getString(R.string.fragment_device_list_fail));
            }
        });
    }

    public void getRemindList(String deviceId, SDKGetRemindListCallback callback) {
        RokidMobileSDK.skill.remind().getList(deviceId, callback);
    }
}
