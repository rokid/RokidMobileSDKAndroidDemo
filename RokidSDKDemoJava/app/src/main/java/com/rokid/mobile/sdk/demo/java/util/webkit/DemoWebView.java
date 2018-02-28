package com.rokid.mobile.sdk.demo.java.util.webkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.webview.lib.RKWebBridge;
import com.rokid.mobile.webview.lib.bean.TitleBarButton;
import com.rokid.mobile.webview.lib.delegate.BridgeModuleAppDelegate;
import com.rokid.mobile.webview.lib.delegate.BridgeModuleViewDelegate;
import com.rokid.mobile.webview.lib.module.BridgeModuleApp;
import com.rokid.mobile.webview.lib.module.BridgeModulePhone;
import com.rokid.mobile.webview.lib.module.BridgeModuleView;

import java.lang.ref.WeakReference;

/**
 * Created by wangshuwen on 2017/5/17.
 */
public class DemoWebView extends WebView implements
        BridgeModuleViewDelegate, BridgeModuleAppDelegate {

    private WeakReference<Context> activityWeak;

    public DemoWebView(Context context) {
        super(context);
        init(context);
    }

    public DemoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DemoWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        Logger.i("init is called context=" + context);

        activityWeak = new WeakReference<>(context);

        WebSettings webSettings = getSettings();
        // 截取所有的a标签带 target=”_blank”执行跳转新的页面
        webSettings.setSupportMultipleWindows(true);
        // 开启 JavaScript 支持
        webSettings.setJavaScriptEnabled(true);
        // 开始 css 适配
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 开启AppCache
        webSettings.setAppCacheEnabled(true);
        // 开启本地存储
        webSettings.setDomStorageEnabled(true);
        // 允许https页面访问http图片 安卓5.0以上是默认不允许的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 允许https页面访问http图片 安卓5.0以上是默认不允许的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // Bridge V1.0.0
        RKWebBridge rokidWebBridge = new RKWebBridge(this);

        // 注册模块
        rokidWebBridge.register(new BridgeModulePhone());
        rokidWebBridge.register(new BridgeModuleView(this));
        rokidWebBridge.register(new BridgeModuleApp(this));

        addJavascriptInterface(rokidWebBridge, RKWebBridge.Companion.getBridgeName());

        DemoWebViewClient webViewClient = new DemoWebViewClient(rokidWebBridge);

        this.setWebViewClient(webViewClient);

        this.setWebChromeClient(new DemoWebChromeClient());
    }

    @Override
    public void close() {

    }

    @Override
    public void open(String title, String url) {
        this.loadUrl(url);
    }

    @Override
    public void openNewWebView(String title, String url) {
        if (null == activityWeak || null == activityWeak.get()) {
            Logger.e("loadNewWebView failed cause by activity is null !!!!");
            return;
        }

        if (TextUtils.isEmpty(url)) {
            Logger.e("The url is null !!!!");
            return;
        }

        Logger.i("Start to load the new WebView");
        Logger.d("title: " + " ;url: " + url);

        Intent intent = new Intent(activityWeak.get(), DemoWebviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(DemoWebviewActivity.KEY_TITLE, title);
        intent.putExtra(DemoWebviewActivity.KEY_URL, url);

        activityWeak.get().startActivity(intent);
    }

    @Override
    public void openExternal(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);

        activityWeak.get().startActivity(intent);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setTitleBarStyle(String style) {

    }

    @Override
    public void setTitleBarRight(TitleBarButton titleBarButton) {

    }

    @Override
    public void setTitleBarRightDotState(boolean state) {

    }

    @Override
    public void errorView(boolean state, String retryUrl) {

    }
}
