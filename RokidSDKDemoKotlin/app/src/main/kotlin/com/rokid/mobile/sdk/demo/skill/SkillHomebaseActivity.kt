package com.rokid.mobile.sdk.demo.skill

import android.view.KeyEvent
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseActivity
import kotlinx.android.synthetic.main.skill_activity_homebase.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillHomebaseActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.skill_activity_homebase
    }

    override fun initViews() {
    }

    override fun initListeners() {
        skill_titlebar_left.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
//        skill_homebase_webview.loadUrl("file:///android_asset/WebBridge_V1.html")
        skill_homebase_webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/dev/index.html#/homes/index")
//        skill_homebase_webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/pre/index.html#/homes/index")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && skill_homebase_webview.canGoBack()) {
            skill_homebase_webview.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

}