package com.rokid.mobile.sdk.demo.skill

import android.annotation.SuppressLint
import com.rokid.mobile.lib.xbase.channel.IChannelPublishCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.annotation.SDKRepeatType
import com.rokid.mobile.sdk.bean.SDKAlarm
import com.rokid.mobile.sdk.callback.SDKOperateAlarmCallback
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
            val newAlarm = SDKAlarm().apply {
                year = 2019
                month = 2
                day = 1
                hour = alarm_time.hour
                minute = alarm_time.minute
                repeatType = SDKRepeatType.ONCE
                ext = HashMap<String, String>().apply {
                    put("NewTestKey", "NewTestValue")
                }
            }

            //修改云闹钟
//            alarm?.hour = alarm_time.hour
//            alarm?.minute = alarm_time.minute
//            RokidMobileSDK.skill.cloudAlarm().updateAlarm(deviceId, alarm, object : SDKOperateAlarmCallback {
//                override fun onSucceed() {
//                    runOnUiThread {
//                        toast("修改成功")
//                        finish()
//                    }
//
//                }
//
//                override fun onFailed(p0: String?, p1: String?) {
//                    runOnUiThread {
//                        toast("修改失败")
//                    }
//                }
//
//            })

            //修改本地闹钟
            RokidMobileSDK.skill.alarm().update(deviceId!!, alarm, newAlarm,
                    object : IChannelPublishCallback {
                        override fun onSucceed() {
                            toast("修改成功")
                            finish()
                        }

                        override fun onFailed() {
                            toast("修改失败")
                        }
                    })
        }
    }
}