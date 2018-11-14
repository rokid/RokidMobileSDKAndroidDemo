package com.rokid.mobile.sdk.demo

import android.app.Application
import com.rokid.mobile.binder.lib.bluetooth.callBack.IBTConnectCallBack
import com.rokid.mobile.binder.lib.bluetooth.exception.BleException
import com.rokid.mobile.lib.entity.bean.bluetooth.BTDeviceBean
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.callback.SDKInitCompletedCallback

/**
 * Created by wangshuwen on 2017/12/3.
 */
class SDKDemoApplication : Application() {

    companion object {
        private lateinit var instance: Application

        fun getContext(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RokidMobileSDK.init(this,
                "rokid-demo",
                "rokid-demo-secret",
                "rokid-demo-accessKey",
                object : SDKInitCompletedCallback {
                    override fun onInitFailed(p0: String?, p1: String?) {
                    }

                    override fun onInitSuccess() {
                    }

                })

        //RokidMobileSDK.debug()

        RokidMobileSDK.binder.connectBT("", object : IBTConnectCallBack {
            override fun onConnectFailed(p0: BTDeviceBean?, p1: BleException?) {
            }

            override fun onConnectSucceed(p0: BTDeviceBean?) {
            }

        })
    }


}