package com.rokid.mobile.sdk.demo.java.skill;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.xbase.channel.IChannelPublishCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.annotation.SDKRepeatType;
import com.rokid.mobile.sdk.bean.SDKAlarm;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseActivity;
import com.rokid.mobile.sdk.demo.java.view.IconTextView;

import java.util.HashMap;
import java.util.Map;

import static com.rokid.mobile.sdk.demo.java.DemoApplication.getContext;

public class SkillAlarmUpdateActivity extends BaseActivity {

    public final static String DEVICE_ID = "device_id";

    public final static String ALARM = "alarm";

    private RelativeLayout titlebarLl;

    private IconTextView backIcon;

    private TextView titleTxt;

    private TimePicker timePicker;

    private Button updateBtn;

    private String deviceId;

    private SDKAlarm oldAlarm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_skill_alarm_update;
    }

    @Override
    protected void initVariables(@Nullable Bundle savedInstanceState) {
        titlebarLl = (RelativeLayout) findViewById(R.id.alarm_update_titlebar);
        backIcon = titlebarLl.findViewById(R.id.base_titlebar_left);
        titleTxt = titlebarLl.findViewById(R.id.base_titlebar_title);

        timePicker = (TimePicker) findViewById(R.id.alarm_time);
        updateBtn = (Button) findViewById(R.id.alarm_update_btn);

        titleTxt.setText("修改闹钟");
        initTimerPicker();

        deviceId = getIntent().getStringExtra(DEVICE_ID);

        oldAlarm = getIntent().getParcelableExtra(ALARM);
    }

    @Override
    protected void initListeners() {
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlarm();
            }
        });
    }

    private void updateAlarm() {
        Map<String, String> map = new HashMap<>();
        map.put("NewTestKey", "NewTestValue");

        SDKAlarm newAlarm = SDKAlarm.builder()
                .hour(timePicker.getCurrentHour())
                .minute(timePicker.getCurrentMinute())
                .repeatType(SDKRepeatType.EVERY_MONDAY)
                .ext(map)
                .build();

        RokidMobileSDK.skill.alarm().update(deviceId, oldAlarm, newAlarm, new IChannelPublishCallback() {
            @Override
            public void onSucceed() {
                showToastShort("修改闹钟成功");
                finish();
            }

            @Override
            public void onFailed() {
                showToastShort("修改闹钟失败");
            }
        });

    }

    private void initTimerPicker() {
        if (get24HourMode(getContext())) {
            Logger.d("system is 24 hour");
            timePicker.setIs24HourView(true);
        } else {
            Logger.d("system is 12 hour");
            timePicker.setIs24HourView(false);
        }
    }

    private boolean get24HourMode(final Context context) {
        return android.text.format.DateFormat.is24HourFormat(context);
    }
}
