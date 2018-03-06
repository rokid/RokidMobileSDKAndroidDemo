package com.rokid.mobile.sdk.demo.skill

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_homebase.*


/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillHomebaseFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.skill_fragment_homebase
    }

    override fun initViews() {
    }

    override fun initListeners() {
    }

    override fun onResume() {
        super.onResume()
//        skill_homebase_webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/dev/index.html#/homes/index")
        skill_homebase_webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/pre/index.html#/homes/index")
    }

}