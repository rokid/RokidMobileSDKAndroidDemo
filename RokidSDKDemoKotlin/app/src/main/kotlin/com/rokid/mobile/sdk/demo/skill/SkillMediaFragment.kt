package com.rokid.mobile.sdk.demo.skill

import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.media.cloud.data.MediaHomeV3Data
import com.rokid.mobile.lib.entity.bean.media.middleware.SkillBean
import com.rokid.mobile.lib.xbase.media.callback.IGetMediaHomeDataCallBack
import com.rokid.mobile.lib.xbase.media.callback.IMediaWareSkillListCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_media.view.*

/**
 * Description: TODO
 * Author: shper
 * Version: V0.1 2018/11/19
 */

class SkillMediaFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.skill_fragment_media

    override fun initViews() {
    }

    override fun initListeners() {
        rootView!!.skill_media_getskill.setOnClickListener {
            RokidMobileSDK.media.requestSkillListIntent(object : IMediaWareSkillListCallback {
                override fun onSucceed(skillBeanList: MutableList<SkillBean>?) {
                    Logger.d("onSucceed - ${skillBeanList.toString()}")


                    this@SkillMediaFragment.activity?.runOnUiThread {
                        rootView!!.skill_media_response_txt.text = skillBeanList.toString()
                    }
                }

                override fun onFailed(errorCode: String?, errorMessage: String?) {
                    Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")
                    rootView!!.skill_media_response_txt.text = errorMessage
                }

            })
        }

        rootView!!.skill_media_home.setOnClickListener {
            RokidMobileSDK.media.requestHomeIntent("RC528E2DD8E745E195173D9F8BE48436", object : IGetMediaHomeDataCallBack {
                override fun onSucceed(mediaHomeV3Data: MediaHomeV3Data?) {
                    Logger.d("onSucceed - ${mediaHomeV3Data.toString()}")


                    this@SkillMediaFragment.activity?.runOnUiThread {
                        rootView!!.skill_media_response_txt.text = mediaHomeV3Data.toString()
                    }
                }

                override fun onFailed(errorCode: String?, errorMessage: String?) {
                    Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")
                    rootView!!.skill_media_response_txt.text = errorMessage
                }
            })
        }
    }

}