package com.rokid.mobile.sdk.demo.webkit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import java.lang.ref.WeakReference

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class DemoWebview : WebView, BridgeModuleAppDelegate, BridgeModuleViewDelegate {

    private var contextWeak: WeakReference<Context>? = null

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
        contextWeak = WeakReference(context)

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

        val sdkWebViewClient = DemoWebViewClient(rokidWebBridge)

        webViewClient = sdkWebViewClient
        webChromeClient = DemoWebChromeClient()
    }

    // here is BridgeModuleAppDelegate
    override fun close() {
        //contextWeak?.get()?.finish()
    }

    override fun open(title: String, url: String) {
        this.loadUrl(url)
    }

    override fun openNewWebView(title: String, url: String) {
        if (null == contextWeak || null == contextWeak?.get()) {
            return
        }

        val intent = Intent(contextWeak!!.get(), DemoWebviewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(DemoWebviewActivity.KEY_TITLE, title)
        intent.putExtra(DemoWebviewActivity.KEY_URL, url)

        contextWeak!!.get()!!.startActivity(intent)
    }

    override fun openExternal(url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val content_url = Uri.parse(url)
        intent.data = content_url

        contextWeak?.get()?.startActivity(intent)
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