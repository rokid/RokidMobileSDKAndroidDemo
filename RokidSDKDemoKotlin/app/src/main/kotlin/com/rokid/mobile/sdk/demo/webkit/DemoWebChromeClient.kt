package com.rokid.mobile.sdk.demo.webkit

import android.app.AlertDialog
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.rokid.mobile.lib.base.util.Logger

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class DemoWebChromeClient : WebChromeClient() {

    // 这里拦截了js的alert 不然会导致下一个webViewBridge加载阻塞
    override fun onJsAlert(view: WebView?, url: String, message: String, result: JsResult?): Boolean {
        Logger.d("onJsAlert")
        if (view == null) {
            Logger.e("WebView is null,return false")
            return false
        }

        if (result == null) {
            Logger.e("JsResult is null,return false")
            return false
        }

        AlertDialog.Builder(view.context)
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton("知道了") { dialog, which -> Logger.d("onJsAlert - OK onclick") }
                .show()

        result.confirm()
        return true
    }

}
