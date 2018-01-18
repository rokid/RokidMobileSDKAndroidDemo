package com.rokid.mobile.sdk.demo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean
import com.rokid.mobile.sdk.demo.R
import kotlinx.android.synthetic.main.fragment_event.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/1/18
 */
class EventFragment : Fragment() {

    private var rootView: View? = null
    private lateinit var eventTxt: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            rootView = inflater!!.inflate(R.layout.fragment_event, container!!, false)
            initView()
            initListener()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        return rootView
    }

    private fun initView() {
        eventTxt = rootView!!.event_txt
    }

    private fun initListener() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceivedCard(cardMsgBean: CardMsgBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + cardMsgBean.toString())
        eventTxt.append("\n" + cardMsgBean.msgTxt)
    }

}