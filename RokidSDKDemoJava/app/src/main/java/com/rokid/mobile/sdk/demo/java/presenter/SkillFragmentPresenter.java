package com.rokid.mobile.sdk.demo.java.presenter;

import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.fragment.SkillFragment;

/**
 * Created by tt on 2018/2/24.
 */

public class SkillFragmentPresenter extends BaseFragmentPresenter<SkillFragment> {
    public SkillFragmentPresenter(SkillFragment fragment) {
        super(fragment);
    }

    public boolean getAlarmList(String deviceId) {
       return RokidMobileSDK.skill.alarm().getList(deviceId);
    }

    public boolean getReminderList(String deviceId) {
        return RokidMobileSDK.skill.remind().getList(deviceId);
    }
}
