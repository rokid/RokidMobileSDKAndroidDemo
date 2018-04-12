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
import com.rokid.mobile.sdk.bean.SDKDevice;
import com.rokid.mobile.sdk.bean.SDKRemind;
import com.rokid.mobile.sdk.callback.GetRemindListCallback;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class SkillRemindFragment extends BaseFragment<SkillRemindFragmentPresenter> {

    @BindView(R.id.skill_remind_device_id)
    Spinner deviceSp;

    @BindView(R.id.skill_remind_list)
    Button remindBtn;

    @BindView(R.id.fragment_skill_remind_info_txt)
    TextView infoTxt;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_skill_remind;
    }

    @Override
    protected SkillRemindFragmentPresenter initPresenter() {
        return new SkillRemindFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initListeners() {
        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInfoText();

                getPresenter().getRemindList(deviceSp.getSelectedItem().toString(), new GetRemindListCallback() {
                    @Override
                    public void onSucceed(final List<SDKRemind> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToastShort(getString(R.string.fragment_skill_reminder_list_success));

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
                                showToastShort(getString(R.string.fragment_skill_reminder_list_fail));
                            }
                        });
                    }
                });
            }
        });
    }

    public void setDeviceListInfo(List<SDKDevice> deviceList) {
        List<String> deviceIdList = new ArrayList<>();
        for (SDKDevice rkDevice : deviceList) {
            String deviceId = rkDevice.getDeviceId();
            deviceIdList.add(deviceId);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_device_spinner, deviceIdList);
        deviceSp.setAdapter(adapter);
    }

    private void clearInfoText() {
        if (!TextUtils.isEmpty(infoTxt.getText())) {
            infoTxt.setText("");
        }
    }
}
