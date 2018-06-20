package com.rokid.mobile.sdk.demo.java.skill;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
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

public class SkillAlarmAddActivity extends BaseActivity {

    public final static String DEVICE_ID = "device_id";

    private RelativeLayout titlebarLl;

    private IconTextView backIcon;

    private TextView titleTxt;

    private DatePicker datePicker;

    private TimePicker timePicker;

    private Button addBtn;

    private String deviceId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_skill_alarm_add;
    }

    @Override
    protected void initVariables(@Nullable Bundle savedInstanceState) {
        titlebarLl = (RelativeLayout) findViewById(R.id.alarm_add_titlebar);
        backIcon = titlebarLl.findViewById(R.id.base_titlebar_left);
        titleTxt = titlebarLl.findViewById(R.id.base_titlebar_title);

        datePicker = (DatePicker) findViewById(R.id.alarm_date);
        timePicker = (TimePicker) findViewById(R.id.alarm_time);
        addBtn = (Button) findViewById(R.id.alarm_add_btn);

        titleTxt.setText("添加闹钟");
        initTimerPicker();

        deviceId = getIntent().getStringExtra(DEVICE_ID);
    }

    @Override
    protected void initListeners() {
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });
    }

    private void addAlarm() {
        Map<String, String> map = new HashMap<>();
        map.put("TestKey", "TestValue");

        SDKAlarm alarm = SDKAlarm.builder()
                .year(datePicker.getYear())
                .month(datePicker.getMonth() + 1)
                .day(datePicker.getDayOfMonth())
                .hour(timePicker.getCurrentHour())
                .minute(timePicker.getCurrentMinute())
                .repeatType(SDKRepeatType.EVERY_MONDAY)
                .ext(map)
                .build();

        RokidMobileSDK.skill.alarm().add(deviceId, alarm, new IChannelPublishCallback() {
            @Override
            public void onSucceed() {
                showToastShort("添加闹钟成功");
                finish();
            }

            @Override
            public void onFailed() {
                showToastShort("添加闹钟失败");

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
