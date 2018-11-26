package com.rokid.mobile.sdk.demo.skill

import com.rokid.mobile.lib.annotation.ThirdAuth
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.auth.ThirdOauthInfoBean
import com.rokid.mobile.lib.entity.bean.auth.UploadInfoBean
import com.rokid.mobile.lib.entity.bean.media.cloud.data.MediaDetailV3Data
import com.rokid.mobile.lib.entity.bean.media.cloud.data.MediaHomeV3Data
import com.rokid.mobile.lib.entity.bean.media.cloud.data.MediaListV3Data
import com.rokid.mobile.lib.entity.bean.media.cloud.template.MediaEventTemplate
import com.rokid.mobile.lib.entity.bean.media.middleware.MediaWareControlData
import com.rokid.mobile.lib.entity.bean.media.middleware.SkillBean
import com.rokid.mobile.lib.xbase.appserver.callback.IGetThirdOauthInfoCallback
import com.rokid.mobile.lib.xbase.appserver.callback.Thirdauth.IUploadThirdAuthCallback
import com.rokid.mobile.lib.xbase.media.callback.*
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_media.view.*
import java.util.*

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

                    this@SkillMediaFragment.activity?.runOnUiThread {
                        rootView!!.skill_media_response_txt.text = errorMessage
                    }
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

                    this@SkillMediaFragment.activity?.runOnUiThread {
                        rootView!!.skill_media_response_txt.text = errorMessage
                    }
                }
            })
        }

        rootView!!.skill_media_list.setOnClickListener {
            RokidMobileSDK.media.requestListIntent("RC528E2DD8E745E195173D9F8BE48436",
                    "16",
                    0,
                    5,
                    "",
                    object : IGetMediaListDataCallBack {
                        override fun onSucceed(mediaListV3Data: MediaListV3Data?) {
                            Logger.d("onSucceed - ${mediaListV3Data.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaListV3Data.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_detail.setOnClickListener {
            RokidMobileSDK.media.requestDetailIntent("RC528E2DD8E745E195173D9F8BE48436",
                    "16",
                    0,
                    5,
                    "",
                    "",
                    object : IGetMediaWareDetailDataCallBack {
                        override fun onSucceed(mediaDetailV3Data: MediaDetailV3Data?) {
                            Logger.d("onSucceed - ${mediaDetailV3Data.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaDetailV3Data.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_play.setOnClickListener {
            RokidMobileSDK.media.requestPlayIntent("RC528E2DD8E745E195173D9F8BE48436",
                    "326970",
                    object : IMediaWareControlCallback {
                        override fun onSucceed(mediaWareControlData: MediaWareControlData?) {
                            Logger.d("onSucceed - ${mediaWareControlData.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaWareControlData.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_playInfo.setOnClickListener {
            RokidMobileSDK.media.requestPlayInfoIntent("RC528E2DD8E745E195173D9F8BE48436",
                    object : IMediaPlayInfoCallback {
                        override fun onSucceed(mediaEventTemplate: MediaEventTemplate?) {
                            Logger.d("onSucceed - ${mediaEventTemplate.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaEventTemplate.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_pause.setOnClickListener {
            RokidMobileSDK.media.requestPauseIntent("RC528E2DD8E745E195173D9F8BE48436",
                    object : IMediaWareControlCallback {
                        override fun onSucceed(mediaWareControlData: MediaWareControlData?) {
                            Logger.d("onSucceed - ${mediaWareControlData.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaWareControlData.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_resume.setOnClickListener {
            RokidMobileSDK.media.requestResumeIntent("RC528E2DD8E745E195173D9F8BE48436",
                    object : IMediaWareControlCallback {
                        override fun onSucceed(mediaWareControlData: MediaWareControlData?) {
                            Logger.d("onSucceed - ${mediaWareControlData.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaWareControlData.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_next.setOnClickListener {
            RokidMobileSDK.media.requestNextIntent("RC528E2DD8E745E195173D9F8BE48436",
                    object : IMediaWareControlCallback {
                        override fun onSucceed(mediaWareControlData: MediaWareControlData?) {
                            Logger.d("onSucceed - ${mediaWareControlData.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaWareControlData.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }

        rootView!!.skill_media_pre.setOnClickListener {
            RokidMobileSDK.media.requestPreviousIntent("RC528E2DD8E745E195173D9F8BE48436",
                    object : IMediaWareControlCallback {
                        override fun onSucceed(mediaWareControlData: MediaWareControlData?) {
                            Logger.d("onSucceed - ${mediaWareControlData.toString()}")


                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = mediaWareControlData.toString()
                            }
                        }

                        override fun onFailed(errorCode: String?, errorMessage: String?) {
                            Logger.e("onFailed - errorCode: ${errorCode}; errorMessage: ${errorMessage}")

                            this@SkillMediaFragment.activity?.runOnUiThread {
                                rootView!!.skill_media_response_txt.text = errorMessage
                            }
                        }
                    })
        }
    }

}