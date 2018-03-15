package com.rokid.mobile.sdk.demo.webkit

import android.webkit.WebView
import com.rokid.mobile.sdk.webkit.SDKWebChromeClient
import com.rokid.mobile.webview.lib.RKWebBridge

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class DemoWebChromeClient(bridge: RKWebBridge) : SDKWebChromeClient(bridge) {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
    }

}