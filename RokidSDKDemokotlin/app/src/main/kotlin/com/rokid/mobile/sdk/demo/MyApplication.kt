package com.rokid.mobile.sdk.demo

import android.app.Application
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.callback.InitCompletedCallback

/**
 * Created by wangshuwen on 2017/12/3.
 */
class MyApplication : Application() {

    companion object {
        private lateinit var instance: Application

        fun getContext(): Application{
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RokidMobileSDK.init(this, "appId", "appSecret", "", object : InitCompletedCallback {
            override fun onInitFailed(p0: String?, p1: String?) {
            }

            override fun onInitSuccess() {
            }

        })

        RokidMobileSDK.debug()
    }


}