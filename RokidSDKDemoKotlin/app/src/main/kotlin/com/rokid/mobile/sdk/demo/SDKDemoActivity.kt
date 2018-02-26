package com.rokid.mobile.sdk.demo

import android.support.design.widget.TabLayout
import com.rokid.mobile.sdk.demo.account.AccountIndexFragment
import com.rokid.mobile.sdk.demo.base.BaseActivity
import com.rokid.mobile.sdk.demo.binder.BinderIndexFragment
import com.rokid.mobile.sdk.demo.device.DeviceIndexFragment
import com.rokid.mobile.sdk.demo.other.EventFragment
import com.rokid.mobile.sdk.demo.skill.SkillIndexFragment
import kotlinx.android.synthetic.main.demo_activity_index.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SDKDemoActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.demo_activity_index
    }

    override fun initViews() {
    }

    override fun initListeners() {
        demo_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                onTabItemSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        for (position in 0..(MainData.mainFragmentList.size - 1)) {
            demo_tab_layout.addTab(demo_tab_layout.newTab().setText(getTabTitle(position)))
        }
    }

    private fun getTabTitle(position: Int): String {
        return MainData.mainTabStr[position]
    }

    private fun onTabItemSelected(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.demo_container, MainData.mainFragmentList[position]).commit()
    }

    object MainData {
        val mainFragmentList = arrayOf(AccountIndexFragment(),
                DeviceIndexFragment(),
                BinderIndexFragment(),
                SkillIndexFragment(),
                EventFragment())

        val mainTabStr = listOf("登录", "设备", "配网", "技能", "消息")
    }

}



