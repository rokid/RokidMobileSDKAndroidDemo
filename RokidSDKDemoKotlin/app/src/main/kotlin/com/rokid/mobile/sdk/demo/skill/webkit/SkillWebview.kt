package com.rokid.mobile.sdk.demo.skill.webkit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import com.rokid.mobile.webview.lib.RKWebBridge
import com.rokid.mobile.webview.lib.bean.TitleBarButton
import com.rokid.mobile.webview.lib.delegate.BridgeModuleAppDelegate
import com.rokid.mobile.webview.lib.delegate.BridgeModuleViewDelegate
import com.rokid.mobile.webview.lib.module.BridgeModuleApp
import com.rokid.mobile.webview.lib.module.BridgeModulePhone
import com.rokid.mobile.webview.lib.module.BridgeModuleView

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillWebview : WebView, BridgeModuleAppDelegate, BridgeModuleViewDelegate {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(context: Context) {
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.domStorageEnabled = true
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // 注册 Bridge 模块
        val rokidWebBridge = RKWebBridge(this)
        rokidWebBridge.register(BridgeModulePhone())
        rokidWebBridge.register(BridgeModuleApp(this))
        rokidWebBridge.register(BridgeModuleView(this))

        addJavascriptInterface(rokidWebBridge, RKWebBridge.BridgeName)

        val sdkWebViewClient = SkillWebViewClient(rokidWebBridge)

        webViewClient = sdkWebViewClient
        webChromeClient = SkillWebChromeClient()
    }

    // here is BridgeModuleAppDelegate
    override fun close() {
    }

    override fun open(title: String, url: String) {
    }

    override fun openExternal(url: String) {
    }

    override fun openNewWebView(title: String, url: String) {
    }

    // here is BridgeModuleViewDelegate
    override fun errorView(state: Boolean, retryUrl: String) {
    }

    override fun hideLoading() {
    }

    override fun setTitle(title: String) {
    }

    override fun setTitleBarRight(button: TitleBarButton) {
    }

    override fun setTitleBarRightDotState(state: Boolean) {
    }

    override fun setTitleBarStyle(style: String) {
    }

    override fun showLoading(message: String) {
    }

    override fun showToast(message: String) {
    }

}