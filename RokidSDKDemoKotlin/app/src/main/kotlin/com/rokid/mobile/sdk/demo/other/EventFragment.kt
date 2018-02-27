package com.rokid.mobile.sdk.demo.other

import android.widget.TextView
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.other_fragment_event.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/1/18
 */
class EventFragment : BaseFragment() {

    private lateinit var eventTxt: TextView

    override fun layoutId(): Int = R.layout.other_fragment_event

    override fun initViews() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        eventTxt = rootView!!.event_txt
    }

    override fun initListeners() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceivedCard(cardMsgBean: CardMsgBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + cardMsgBean.toString())
        eventTxt.append("\n" + cardMsgBean.msgTxt)
    }

}