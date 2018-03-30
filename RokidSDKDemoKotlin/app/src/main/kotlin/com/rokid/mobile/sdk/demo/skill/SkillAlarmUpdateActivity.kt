package com.rokid.mobile.sdk.demo.skill

import android.annotation.SuppressLint
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.annotation.SDKRepeatType
import com.rokid.mobile.sdk.bean.SDKAlarm
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseActivity
import kotlinx.android.synthetic.main.base_titlebar.*
import kotlinx.android.synthetic.main.skill_activity_alarm_update.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/3/12
 */

class SkillAlarmUpdateActivity : BaseActivity() {

    companion object {
        val DEVICE_ID: String = "device_id"
        val ALARM: String = "alarm"
    }

    var deviceId: String? = ""
    var alarm: SDKAlarm? = null

    override fun layoutId(): Int = R.layout.skill_activity_alarm_update

    override fun initVariable() {
        super.initVariable()

        deviceId = intent.getStringExtra(DEVICE_ID)
        alarm = intent.getParcelableExtra(ALARM)
    }

    override fun initViews() {
        base_titlebar_title.text = "修改闹钟"
        alarm_time.setIs24HourView(true)
    }

    @SuppressLint("NewApi")
    override fun initListeners() {
        base_titlebar_left.setOnClickListener { finish() }

        alarm_update_btn.setOnClickListener {
            val sdkAlarm = SDKAlarm().apply {
                hour = alarm_time.hour
                minute = alarm_time.minute
                repeatType = SDKRepeatType.EVERY_MONDAY
                ext = HashMap<String, String>().apply {
                    put("NewTestKey", "NewTestValue")
                }
            }

            val succeed = RokidMobileSDK.skill.alarm().update(deviceId!!, alarm, sdkAlarm)

            if (succeed) {
                toast("添加成功")
                finish()
            }
        }
    }
}