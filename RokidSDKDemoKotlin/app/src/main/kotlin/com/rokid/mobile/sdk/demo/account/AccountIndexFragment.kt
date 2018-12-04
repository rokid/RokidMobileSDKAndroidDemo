package com.rokid.mobile.sdk.demo.account

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.account_fragment_index.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class AccountIndexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.account_fragment_index

    override fun initViews() {
        rootView!!.account_title.text = "帐号"
        rootView!!.account_subtitle.text = "SDK Account Demo"

        rootView!!.account_tab_layout.setupWithViewPager(rootView!!.account_viewPager)
        rootView!!.account_viewPager.adapter = BaseFragmentAdapter(activity!!.supportFragmentManager,
                AccountData.fragmentList)
    }

    override fun initListeners() {
    }

    object AccountData {
        val fragmentList = listOf(
                BaseFragmentAdapter.Companion.Node("登录", AccountLoginFragment())
        )
    }

}