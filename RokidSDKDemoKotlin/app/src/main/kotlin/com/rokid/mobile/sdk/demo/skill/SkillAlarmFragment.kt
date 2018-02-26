package com.rokid.mobile.sdk.demo.skill

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.event.skill.EventAlarmBean
import com.rokid.mobile.lib.entity.event.skill.EventRemindBean
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import kotlinx.android.synthetic.main.skill_fragment_alarm.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/12
 */
class SkillAlarmFragment : Fragment() {

    private var rootView: View? = null
    private lateinit var eventTxt: TextView
    private lateinit var deviceIdEdit: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            rootView = inflater!!.inflate(R.layout.skill_fragment_alarm, container!!, false)
            initView()
            initListener()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        return rootView
    }

    private fun initView() {
        eventTxt = rootView!!.skill_txt
        deviceIdEdit = rootView!!.skill_device_sn
    }

    private fun initListener() {
        rootView!!.skill_alarm_list.setOnClickListener here@{
            val deviceId = deviceIdEdit.text.toString()
            if (deviceId.isEmpty()) {
                Toast.makeText(activity,"请正确输入 SN",Toast.LENGTH_LONG).show()
                return@here
            }

            RokidMobileSDK.skill.alarm().getList(deviceIdEdit.text.toString())
        }

        rootView!!.skill_remind_list.setOnClickListener here@{
            val deviceId = deviceIdEdit.text.toString()
            if (deviceId.isEmpty()) {
                Toast.makeText(activity,"请正确输入 SN",Toast.LENGTH_LONG).show()
                return@here
            }

            RokidMobileSDK.skill.remind().getList(deviceIdEdit.text.toString())
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlarmEvent(eventAlarm: EventAlarmBean){
        Logger.d("onSysInfo eventDeviceSysUpdate" + Gson().toJson(eventAlarm))
        eventTxt.append("\n" + Gson().toJson(eventAlarm))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlarmEvent(eventRemind: EventRemindBean){
        Logger.d("onSysInfo eventDeviceSysUpdate" + Gson().toJson(eventRemind))
        eventTxt.append("\n" + Gson().toJson(eventRemind))
    }

}