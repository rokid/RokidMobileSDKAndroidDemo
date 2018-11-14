package com.rokid.mobile.sdk.demo.other

import android.widget.Button
import com.rokid.mobile.home.lib.callback.IGetAsrErrorCallback
import com.rokid.mobile.lib.entity.bean.home.AsrErrorCorrectBean
import com.rokid.mobile.lib.xbase.channel.IChannelPublishCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.other_fragment_more.view.*

/**
 * @author hongquan.zhao
 * Create Date:2018/3/21
 * Version:V1.0
 */
class MoreFragment : BaseFragment() {

    lateinit var asrBtn: Button
    lateinit var ttsBtn: Button

    override fun layoutId(): Int = R.layout.other_fragment_more

    override fun initViews() {
        asrBtn = rootView!!.fragment_more_asr_btn
        ttsBtn = rootView!!.fragment_more_tts_btn
    }

    override fun initListeners() {
        ttsBtn.setOnClickListener {
            sendTTS()
        }

        asrBtn.setOnClickListener {
            asrCorrect("东冯破", "东风破")
        }

    }

    private fun sendTTS() {
        // TODO 这里devceId 请设置 真实的值
        RokidMobileSDK.vui.sendTts("XXXXXX","哈哈", object : IChannelPublishCallback{
            override fun onSucceed() {
                // TODO ...
            }

            override fun onFailed() {
                // TODO ...
            }
        })
    }

    private fun asrCorrect(originText: String, newText: String) {
        RokidMobileSDK.vui.asrCorrectCreate(originText, newText, object : IGetAsrErrorCallback {
            override fun onGetAsrErrorFailed(p0: String?, p1: String?) {
                toast(getString(R.string.fragment_asr_add_fail)
                        + "\n errorCode = " + p0 + " , errorMsg =  " + p1)
            }

            override fun onGetAsrErrorSucceed(p0: AsrErrorCorrectBean?) {
                toast(getString(R.string.fragment_asr_add_success)
                        + "\n  = " + p0)
            }

        })
    }
}
