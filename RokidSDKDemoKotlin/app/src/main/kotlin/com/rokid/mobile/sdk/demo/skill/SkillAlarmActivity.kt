package com.rokid.mobile.sdk.demo.skill

import android.os.Bundle
import com.google.gson.Gson
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.event.skill.EventAlarmBean
import com.rokid.mobile.lib.entity.event.skill.EventRemindBean
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseActivity
import kotlinx.android.synthetic.main.skill_fragment_alarm.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillAlarmActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.skill_fragment_alarm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun initViews() {
    }

    override fun initListeners() {
        skill_alarm_list.setOnClickListener here@ {
            val deviceId = skill_device_sn.text.toString()
            if (deviceId.isEmpty()) {
                toast("请正确输入 SN")
                return@here
            }

            RokidMobileSDK.skill.alarm().getList(skill_device_sn.text.toString())
        }

        skill_remind_list.setOnClickListener here@ {
            val deviceId = skill_device_sn.text.toString()
            if (deviceId.isEmpty()) {
                toast("请正确输入 SN")
                return@here
            }

            RokidMobileSDK.skill.remind().getList(skill_device_sn.text.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlarmEvent(eventAlarm: EventAlarmBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + Gson().toJson(eventAlarm))
        skill_txt.append("\n" + Gson().toJson(eventAlarm))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlarmEvent(eventRemind: EventRemindBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + Gson().toJson(eventRemind))
        skill_txt.append("\n" + Gson().toJson(eventRemind))
    }
}