package com.rokid.mobile.sdk.demo.java.webkit;

import android.webkit.WebView;

import com.rokid.mobile.sdk.webkit.SDKWebChromeClient;
import com.rokid.mobile.webview.lib.RKWebBridge;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/12/6
 */
public class DemoWebChromeClient extends SDKWebChromeClient {

    public DemoWebChromeClient(RKWebBridge webBridge) {
        super(webBridge);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

}
