package com.rokid.mobile.sdk.demo.java.skill;

import com.rokid.mobile.lib.base.util.CollectionUtils;
import com.rokid.mobile.lib.entity.bean.device.RKDevice;
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
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
        RokidMobileSDK.device.getDeviceList(new IGetDeviceListCallback() {
            @Override
            public void onGetDeviceListSucceed(List<RKDevice> list) {
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

    public boolean getRemindList(String deviceId) {
        return RokidMobileSDK.skill.remind().getList(deviceId);
    }
}
