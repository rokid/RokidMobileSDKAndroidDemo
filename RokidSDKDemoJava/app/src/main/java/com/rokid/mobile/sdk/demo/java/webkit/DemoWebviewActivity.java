package com.rokid.mobile.sdk.demo.java.webkit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseActivity;
import com.rokid.mobile.sdk.demo.java.view.IconTextView;

/**
 * Created by tt on 2018/2/28.
 */

public class DemoWebviewActivity extends BaseActivity {

    public static final String KEY_URL = "url";

    public static final String KEY_TITLE = "title";

    private TextView titleTxt;

    private IconTextView backIcon;

    private DemoWebView webView;

    private String title = "Rokid SDK Demo";

    private String url = "https://www.rokid.com";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo_webview;
    }

    @Override
    protected void initVariables(@Nullable Bundle savedInstanceState) {
        title = getIntent().getStringExtra(KEY_TITLE);
        url = getIntent().getStringExtra(KEY_URL);

        titleTxt = findViewById(R.id.webkit_titlebar_title);
        backIcon = findViewById(R.id.webkit_titlebar_left);
        webView = findViewById(R.id.webkit_webview);

        titleTxt.setText(title);
        webView.loadUrl(url);

    }

    @Override
    protected void initListeners() {
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
