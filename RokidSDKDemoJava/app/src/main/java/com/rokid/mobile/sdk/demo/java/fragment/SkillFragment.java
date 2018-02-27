package com.rokid.mobile.sdk.demo.java.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.entity.event.skill.EventAlarmBean;
import com.rokid.mobile.lib.entity.event.skill.EventRemindBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.presenter.SkillFragmentPresenter;
import com.rokid.mobile.sdk.demo.java.util.SoftKeyBoardUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class SkillFragment extends BaseFragment<SkillFragmentPresenter> {

    @BindView(R.id.fragment_skill_device_id_edit)
    TextInputEditText deviceIdTxt;

    @BindView(R.id.fragment_skill_alarm_btn)
    Button alarmBtn;

    @BindView(R.id.fragment_skill_reminder_btn)
    Button reminderBtn;

    @BindView(R.id.fragment_skill_info_txt)
    TextView infoTxt;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_skill;
    }

    @Override
    protected SkillFragmentPresenter initPresenter() {
        return new SkillFragmentPresenter(this);
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
                if (TextUtils.isEmpty(deviceIdTxt.getText())) {
                    showToastShort(getString(R.string.fragment_skill_devie_id));
                    return;
                }

                SoftKeyBoardUtil.hideSoftKeyboard(getContext(), alarmBtn);
                clearInfoText();
                if (getPresenter().getAlarmList(deviceIdTxt.getText().toString())) {
                    showToastShort(getString(R.string.fragment_skill_alarm_list_success));
                } else {
                    showToastShort(getString(R.string.fragment_skill_alarm_list_fail));
                }
            }
        });

        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(deviceIdTxt.getText())) {
                    showToastShort(getString(R.string.fragment_skill_devie_id));
                    return;
                }

                SoftKeyBoardUtil.hideSoftKeyboard(getContext(), reminderBtn);
                clearInfoText();
                if (getPresenter().getReminderList(deviceIdTxt.getText().toString())) {
                    showToastShort(getString(R.string.fragment_skill_reminder_list_success));
                } else {
                    showToastShort(getString(R.string.fragment_skill_reminder_list_fail));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmEvent(EventAlarmBean eventAlarm) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + new Gson().toJson(eventAlarm));
        clearInfoText();
        infoTxt.setText(new Gson().toJson(eventAlarm));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmEvent(EventRemindBean eventRemind) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + new Gson().toJson(eventRemind));
        clearInfoText();
        infoTxt.setText(new Gson().toJson(eventRemind));
    }

    private void clearInfoText() {
        if (!TextUtils.isEmpty(infoTxt.getText())) {
            infoTxt.setText("");
        }
    }
}
