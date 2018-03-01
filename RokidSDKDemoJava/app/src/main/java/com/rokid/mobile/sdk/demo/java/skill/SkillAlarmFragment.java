package com.rokid.mobile.sdk.demo.java.skill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.entity.bean.device.RKDevice;
import com.rokid.mobile.lib.entity.event.skill.EventAlarmBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class SkillAlarmFragment extends BaseFragment<SkillAlarmFragmentPresenter> {

    @BindView(R.id.skill_alarm_device_id)
    Spinner deviceSp;

    @BindView(R.id.skill_alarm_list)
    Button alarmBtn;

    @BindView(R.id.fragment_skill_info_txt)
    TextView infoTxt;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_skill_alarm;
    }

    @Override
    protected SkillAlarmFragmentPresenter initPresenter() {
        return new SkillAlarmFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initListeners() {
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInfoText();
                if (getPresenter().getAlarmList(deviceSp.getSelectedItem().toString())) {
                    showToastShort(getString(R.string.fragment_skill_alarm_list_success));
                } else {
                    showToastShort(getString(R.string.fragment_skill_alarm_list_fail));
                }
            }
        });
    }

    public void setDeviceListInfo(List<RKDevice> deviceList) {
        List<String> deviceIdList = new ArrayList<>();
        for (RKDevice rkDevice : deviceList) {
            String deviceId = rkDevice.getRokiId();
            deviceIdList.add(deviceId);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_device_spinner, deviceIdList);
        deviceSp.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmEvent(EventAlarmBean eventAlarm) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + new Gson().toJson(eventAlarm));
        clearInfoText();
        infoTxt.setText(new Gson().toJson(eventAlarm));
    }

    private void clearInfoText() {
        if (!TextUtils.isEmpty(infoTxt.getText())) {
            infoTxt.setText("");
        }
    }
}
