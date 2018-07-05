package com.rokid.mobile.sdk.demo.other

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.other_fragment_index.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class OtherIndexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.other_fragment_index

    override fun initViews() {
        rootView!!.other_title.text = "其他"
        rootView!!.other_subtitle.text = "SDK Other Demo"

        rootView!!.other_tab_layout.setupWithViewPager(rootView!!.other_viewPager)
        rootView!!.other_viewPager.adapter = BaseFragmentAdapter(activity.supportFragmentManager,
                OtherData.fragmentList)
    }

    override fun initListeners() {
    }

    object OtherData {
        val fragmentList = listOf(
                BaseFragmentAdapter.Companion.Node("消息", EventFragment()),
                BaseFragmentAdapter.Companion.Node("card列表", CardListFragment()),
                BaseFragmentAdapter.Companion.Node("更多功能", MoreFragment())
        )
    }

}