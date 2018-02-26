package com.rokid.mobile.sdk.demo.skill.webkit

import android.webkit.WebView
import com.rokid.mobile.sdk.webkit.SDKWebviewClient
import com.rokid.mobile.webview.lib.RKWebBridge

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillWebViewClient(bridge: RKWebBridge) : SDKWebviewClient(bridge) {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url)
        return true
    }

}