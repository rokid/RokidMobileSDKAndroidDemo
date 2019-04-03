package com.rokid.mobile.sdk.demo.webkit

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseActivity
import kotlinx.android.synthetic.main.demo_activity_webview.*

/**
 * Author: Shper
 * Version: V0.1 2018/2/27
 */
class DemoWebviewActivity : BaseActivity() {

    private var title: String = "Rokid SDK Demo"
    private var url: String = "https://www.rokid.com"

    companion object {
        val KEY_URL: String = "url"
        val KEY_TITLE: String = "title"
    }

    override fun layoutId(): Int = R.layout.demo_activity_webview

    override fun initVariable() {
        super.initVariable()

        title = intent.getStringExtra(KEY_TITLE)
        url = intent.getStringExtra(KEY_URL)
    }

    override fun initViews() {
        webkit_titlebar_title.text = title
        webkit_webview.loadUrl(url)
    }

    override fun initListeners() {
        webkit_titlebar_left.setOnClickListener {
            finish()
        }
    }

}