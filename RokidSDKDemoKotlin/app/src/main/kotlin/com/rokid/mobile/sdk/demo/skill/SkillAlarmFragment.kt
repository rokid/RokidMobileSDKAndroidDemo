package com.rokid.mobile.sdk.demo.skill

import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.gson.Gson
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.bean.SDKAlarm
import com.rokid.mobile.sdk.bean.SDKDevice
import com.rokid.mobile.sdk.callback.SDKGetAlarmListCallback
import com.rokid.mobile.sdk.callback.SDKGetDeviceListCallback
import com.rokid.mobile.sdk.callback.SDKOperateAlarmCallback
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_alarm.*
import kotlinx.android.synthetic.main.skill_fragment_alarm.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/12
 */
class SkillAlarmFragment : BaseFragment() {

    private lateinit var eventTxt: TextView

    private var alarmList: MutableList<SDKAlarm>? = null

    override fun layoutId(): Int = R.layout.skill_fragment_alarm

    override fun initViews() {
        eventTxt = rootView!!.skill_txt
    }

    override fun initListeners() {
        rootView!!.skill_alarm_add.setOnClickListener {
            val deviceId = skill_alarm_device_id.selectedItem.toString()

            val intent = Intent(activity, SkillAlarmAddActivity::class.java)
            intent.putExtra(SkillAlarmAddActivity.DEVICE_ID, deviceId)
            activity?.startActivity(intent)
        }

        rootView!!.skill_alarm_update.setOnClickListener here@{
            if (null == alarmList || alarmList!!.size <= 0) {
                toast("闹钟列表为空，请先获取 或者创建一个闹钟。")
                return@here
            }

            val deviceId = skill_alarm_device_id.selectedItem.toString()

            val intent = Intent(activity, SkillAlarmUpdateActivity::class.java)
            intent.putExtra(SkillAlarmUpdateActivity.DEVICE_ID, deviceId)
            intent.putExtra(SkillAlarmUpdateActivity.ALARM, alarmList!![0])
            activity?.startActivity(intent)
        }

        rootView!!.skill_alarm_list.setOnClickListener here@{
            val deviceId = skill_alarm_device_id.selectedItem.toString()
            if (deviceId.isEmpty()) {
                toast("请正确输入 SN")
                return@here
            }

            //获取云闹钟列表
//            RokidMobileSDK.skill.cloudAlarm().getList(deviceId, object : SDKGetAlarmListCallback {
//                override fun onSucceed(alarmList: MutableList<SDKAlarm>?) {
//                    this@SkillAlarmFragment.alarmList = alarmList
//
//                    activity!!.runOnUiThread {
//                        eventTxt.text = "\n" + Gson().toJson(alarmList)
//                    }
//                }
//
//                override fun onFailed(errorCode: String?, errorMessage: String?) {
//                    activity!!.runOnUiThread {
//                        eventTxt.text = "\n errorCode:${errorCode}; errorMessage:${errorMessage}"
//                    }
//                }
//
//            })

            //获取本地闹钟列表
            RokidMobileSDK.skill.alarm().getList(skill_alarm_device_id.selectedItem.toString(),
                    object : SDKGetAlarmListCallback {
                        override fun onSucceed(alarmList: MutableList<SDKAlarm>?) {
                            this@SkillAlarmFragment.alarmList = alarmList

                            activity!!.runOnUiThread {
                                eventTxt.text = "\n" + Gson().toJson(alarmList)
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            activity!!.runOnUiThread {
                                eventTxt.text = "\n errorCode:${errorCode}; errorMessage:${errorMessage}"
                            }
                        }
                    })
        }

    }

    override fun onResume() {
        super.onResume()

        setDeviceList()
    }

    private fun setDeviceList() {
        RokidMobileSDK.device.getDeviceList(object : SDKGetDeviceListCallback {

            override fun onGetDeviceListSucceed(deviceList: MutableList<SDKDevice>?) {
                if (null == deviceList || deviceList.size < 1) {
                    toast("设备列表为空")
                    return
                }

                val deviceIdList: MutableList<String> = mutableListOf()
                deviceList.forEach {
                    deviceIdList.add(it.deviceId)
                }

                skill_alarm_device_id.adapter = ArrayAdapter<String>(activity,
                        R.layout.base_spinner_item, deviceIdList)
            }

            override fun onGetDeviceListFailed(errorCode: String?, errorMessage: String?) {
            }

        })
    }

}