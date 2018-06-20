package com.rokid.mobile.sdk.demo.skill

import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.bean.SDKDevice
import com.rokid.mobile.sdk.bean.SDKRemind
import com.rokid.mobile.sdk.callback.SDKGetDeviceListCallback
import com.rokid.mobile.sdk.callback.SDKGetRemindListCallback
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_remind.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/12
 */
class SkillRemindFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.skill_fragment_remind

    override fun initViews() {
    }

    override fun initListeners() {
        rootView!!.skill_remind_list.setOnClickListener here@ {
            val deviceId = rootView!!.skill_remind_device_id.selectedItem.toString()
            if (deviceId.isEmpty()) {
                toast("请正确输入 SN")
                return@here
            }

            RokidMobileSDK.skill.remind().getList(rootView!!.skill_remind_device_id.selectedItem.toString(),
                    object : SDKGetRemindListCallback {
                        override fun onSucceed(remindList: MutableList<SDKRemind>?) {
                            activity.runOnUiThread {
                                rootView!!.skill_txt.append("\n" + Gson().toJson(remindList))
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            activity.runOnUiThread {
                                rootView!!.skill_txt.append("\n errorCode:${errorCode}; errorMessage:${errorMessage}")
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

                rootView!!.skill_remind_device_id.adapter = ArrayAdapter<String>(activity,
                        R.layout.base_spinner_item, deviceIdList)
            }

            override fun onGetDeviceListFailed(errorCode: String?, errorMessage: String?) {
            }

        })
    }

}