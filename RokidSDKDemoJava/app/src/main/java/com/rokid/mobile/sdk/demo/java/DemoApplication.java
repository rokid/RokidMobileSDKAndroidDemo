package com.rokid.mobile.sdk.demo.java;

import android.app.Application;
import android.util.Log;

import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.callback.InitCompletedCallback;

/**
 * Created by tt on 2018/2/24.
 */

public class DemoApplication extends Application {

    private static final String TAG = DemoApplication.class.getSimpleName();

    private static Application instance;

    public static Application getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //initial rokid sdk
        RokidMobileSDK.init(this,
                "your rokid appKey",
                "your rokid appSecret",
                "your rokid accessKey",
                new InitCompletedCallback() {
                    @Override
                    public void onInitSuccess() {
                        Log.d(TAG, "initial rokid sdk success");
                    }

                    @Override
                    public void onInitFailed(String s, String s1) {
                        Log.d(TAG, "initial rokid sdk failed");
                        Log.d(TAG, "errorCode = " + s + " , errorMsg" + s1);
                    }
                });

        //切换到测试环境
//        RokidMobileSDK.debug();
    }

}
