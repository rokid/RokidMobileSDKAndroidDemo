package com.rokid.mobile.sdk.demo.java.skill;

import android.content.Intent;
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
import com.rokid.mobile.lib.base.util.CollectionUtils;
import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.sdk.bean.SDKAlarm;
import com.rokid.mobile.sdk.bean.SDKDevice;
import com.rokid.mobile.sdk.callback.GetAlarmListCallback;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class SkillAlarmFragment extends BaseFragment<SkillAlarmFragmentPresenter> {

    @BindView(R.id.skill_alarm_device_id)
    Spinner deviceSp;

    @BindView(R.id.skill_alarm_add)
    Button alarmAddBtn;

    @BindView(R.id.skill_alarm_update)
    Button alarmUpdateBtn;

    @BindView(R.id.skill_alarm_list)
    Button alarmBtn;

    @BindView(R.id.fragment_skill_info_txt)
    TextView infoTxt;

    private List<SDKAlarm> alarmList;

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
    }

    @Override
    protected void initListeners() {
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInfoText();
                getPresenter().getAlarmList(deviceSp.getSelectedItem().toString(),
                        new GetAlarmListCallback() {
                            @Override
                            public void onSucceed(final List<SDKAlarm> list) {
                                showToastShort(getString(R.string.fragment_skill_alarm_list_success));
                                alarmList = list;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        clearInfoText();
                                        infoTxt.setText(new Gson().toJson(list));
                                    }
                                });
                            }

                            @Override
                            public void onFailed(String errorCode, String errorMessage) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToastShort(getString(R.string.fragment_skill_alarm_list_fail));
                                    }
                                });
                            }
                        });
            }
        });

        alarmAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SkillAlarmAddActivity.class);
                intent.putExtra(SkillAlarmAddActivity.DEVICE_ID, deviceSp.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        alarmUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CollectionUtils.isEmpty(alarmList)) {
                    showToastShort("闹钟列表为空，请先获取 或者创建一个闹钟。");
                    return;
                }

                Intent intent = new Intent(getActivity(), SkillAlarmUpdateActivity.class);
                intent.putExtra(SkillAlarmUpdateActivity.DEVICE_ID, deviceSp.getSelectedItem().toString());
                intent.putExtra(SkillAlarmUpdateActivity.ALARM, alarmList.get(0));
                startActivity(intent);
            }
        });
    }

    public void setDeviceListInfo(List<SDKDevice> deviceList) {
        List<String> deviceIdList = new ArrayList<>();
        for (SDKDevice rkDevice : deviceList) {
            String deviceId = rkDevice.getRokiId();
            deviceIdList.add(deviceId);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_device_spinner,
                deviceIdList);

        deviceSp.setAdapter(adapter);
    }

    private void clearInfoText() {
        if (!TextUtils.isEmpty(infoTxt.getText())) {
            infoTxt.setText("");
        }
    }
}
