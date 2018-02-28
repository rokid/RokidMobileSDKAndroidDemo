package com.rokid.mobile.sdk.demo.java.util.webkit;

import android.webkit.WebView;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.sdk.webkit.SDKWebviewClient;
import com.rokid.mobile.webview.lib.RKWebBridge;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/12/6
 */
public class DemoWebViewClient extends SDKWebviewClient {

    public DemoWebViewClient(RKWebBridge webBridge) {
        super(webBridge);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.d("The shouldOverrideUrlLoading...");

        if (null != view) {
            view.loadUrl(url);
        }
        return true;
    }
}
