package com.rokid.mobile.sdk.demo.skill

import android.widget.*
import com.google.gson.Gson
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.device.RKDevice
import com.rokid.mobile.lib.entity.event.skill.EventAlarmBean
import com.rokid.mobile.lib.entity.event.skill.EventRemindBean
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_alarm.*
import kotlinx.android.synthetic.main.skill_fragment_alarm.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/12
 */
class SkillAlarmFragment : BaseFragment() {

    private lateinit var eventTxt: TextView

    override fun layoutId(): Int = R.layout.skill_fragment_alarm

    override fun initViews() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        eventTxt = rootView!!.skill_txt
        setDeviceList()
    }

    override fun initListeners() {
        rootView!!.skill_alarm_list.setOnClickListener here@ {
            val deviceId = skill_alarm_device_id.selectedItem.toString()
            if (deviceId.isEmpty()) {
                toast("请正确输入 SN")
                return@here
            }

            RokidMobileSDK.skill.alarm().getList(skill_alarm_device_id.selectedItem.toString())
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlarmEvent(eventAlarm: EventAlarmBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + Gson().toJson(eventAlarm))
        eventTxt.append("\n" + Gson().toJson(eventAlarm))
    }

    private fun setDeviceList() {
        RokidMobileSDK.device.getDeviceList(object : IGetDeviceListCallback {

            override fun onGetDeviceListSucceed(deviceList: MutableList<RKDevice>?) {
                if (null == deviceList || deviceList.size < 1) {
                    toast("设备列表为空")
                    return
                }

                val deviceIdList: MutableList<String> = mutableListOf()
                deviceList.forEach {
                    deviceIdList.add(it.rokiId)
                }

                skill_alarm_device_id.adapter = ArrayAdapter<String>(activity,
                        R.layout.base_spinner_item, deviceIdList)
            }

            override fun onGetDeviceListFailed(errorCode: String?, errorMessage: String?) {
            }

        })
    }

}