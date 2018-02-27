package com.rokid.mobile.sdk.demo.skill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.device.RKDevice
import com.rokid.mobile.lib.entity.event.skill.EventRemindBean
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_remind.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/12
 */
class SkillRemindFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.skill_fragment_remind

    override fun initViews() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        setDeviceList()
    }

    override fun initListeners() {
        rootView!!.skill_remind_list.setOnClickListener here@ {
            val deviceId = rootView!!.skill_remind_device_id.selectedItem.toString()
            if (deviceId.isEmpty()) {
                toast("请正确输入 SN")
                return@here
            }

            RokidMobileSDK.skill.remind().getList(rootView!!.skill_remind_device_id.selectedItem.toString())
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlarmEvent(eventRemind: EventRemindBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + Gson().toJson(eventRemind))
        rootView!!.skill_txt.append("\n" + Gson().toJson(eventRemind))
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

                rootView!!.skill_remind_device_id.adapter = ArrayAdapter<String>(activity,
                        R.layout.base_spinner_item, deviceIdList)
            }

            override fun onGetDeviceListFailed(errorCode: String?, errorMessage: String?) {
            }

        })
    }

}