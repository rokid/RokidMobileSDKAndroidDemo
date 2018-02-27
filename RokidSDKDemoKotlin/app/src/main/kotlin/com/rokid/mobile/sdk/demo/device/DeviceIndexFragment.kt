package com.rokid.mobile.sdk.demo.device

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseActivity
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.device_fragment_index.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class DeviceIndexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.device_fragment_index

    override fun initViews() {
        rootView!!.device_title.text = "设备"
        rootView!!.device_subtitle.text = "SDK Device Demo"

        rootView!!.device_tab_layout.setupWithViewPager(rootView!!.device_viewPager)
        rootView!!.device_viewPager.adapter = BaseFragmentAdapter(activity.supportFragmentManager,
                DeviceData.fragmentList)
    }

    override fun initListeners() {
    }

    object DeviceData {
        val fragmentList = listOf(
                BaseFragmentAdapter.Companion.Node("绑定", DeviceBinderFragment()),
                BaseFragmentAdapter.Companion.Node("设备列表", DeviceListFragment())
        )
    }

}