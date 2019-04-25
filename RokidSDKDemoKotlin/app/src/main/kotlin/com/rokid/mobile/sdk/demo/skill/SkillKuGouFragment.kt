package com.rokid.mobile.sdk.demo.skill

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_store.*

/**
 * @author yc
 * @since 2019/04/25
 */
class SkillKuGouFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.skill_fragment_store
    }

    override fun initViews() {
    }

    override fun initListeners() {
    }

    override fun onResume() {
        super.onResume()
        skill_store_webview.loadUrl("https://s.rokidcdn.com/mobile-app/h5/media/index.html#/kugou/userInfo")
    }

}