package com.rokid.mobile.sdk.demo.java.util.webkit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.rokid.mobile.lib.base.util.Logger;

/**
 * Created by tt on 2018/2/28.
 */
public class DemoWebChromeClient extends WebChromeClient {

    // 这里拦截了js的alert 不然会导致下一个webViewBridge加载阻塞
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Logger.d("onJsAlert");
        if (view == null) {
            Logger.e("WebView is null,return false");
            return false;
        }

        if (result == null) {
            Logger.e("JsResult is null,return false");
            return false;
        }

        new AlertDialog.Builder(view.getContext())
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logger.d("onJsAlert - OK onclick");
                    }
                })
                .show();

        result.confirm();
        return true;
    }
}
