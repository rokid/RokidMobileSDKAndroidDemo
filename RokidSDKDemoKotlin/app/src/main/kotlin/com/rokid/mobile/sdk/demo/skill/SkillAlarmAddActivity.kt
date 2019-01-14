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
import kotlinx.android.synthetic.main.skill_activity_alarm_add.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/3/2
 */
class SkillAlarmAddActivity : BaseActivity() {

    companion object {
        val DEVICE_ID: String = "device_id"
    }

    var deviceId: String? = ""

    override fun layoutId(): Int = R.layout.skill_activity_alarm_add

    override fun initVariable() {
        super.initVariable()
        deviceId = intent.getStringExtra(DEVICE_ID)
    }

    override fun initViews() {
        base_titlebar_title.text = "添加闹钟"
        alarm_time.setIs24HourView(true)
    }

    @SuppressLint("NewApi")
    override fun initListeners() {
        base_titlebar_left.setOnClickListener { finish() }

        alarm_add_btn.setOnClickListener {
            val sdkAlarm = SDKAlarm().apply {
                year = alarm_date.year
                month = (alarm_date.month + 1)
                day = alarm_date.dayOfMonth
                hour = alarm_time.hour
                minute = alarm_time.minute
                repeatType = SDKRepeatType.ONCE
                ext = HashMap<String, String>().apply {
                    put("TestKey", "TestValue")
                }
            }

            //添加云闹钟
//            RokidMobileSDK.skill.cloudAlarm().addAlarm(deviceId, sdkAlarm, object : SDKOperateAlarmCallback {
//                override fun onSucceed() {
//                    toast("添加成功")
//                    finish()
//                }
//
//                override fun onFailed(p0: String?, p1: String?) {
//                    toast("添加失败")
//                }
//
//            })

            //添加本地闹钟
            RokidMobileSDK.skill.alarm().add(deviceId!!, sdkAlarm, object : IChannelPublishCallback {
                override fun onSucceed() {
                    toast("添加成功")
                    finish()
                }

                override fun onFailed() {
                    toast("添加失败")
                }

            })

        }
    }

}