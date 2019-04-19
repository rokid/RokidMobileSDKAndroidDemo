package com.rokid.mobile.sdk.demo

import android.app.AlertDialog
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.widget.TextView
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.account.AccountIndexFragment
import com.rokid.mobile.sdk.demo.base.BaseActivity
import com.rokid.mobile.sdk.demo.base.IconTextView
import com.rokid.mobile.sdk.demo.device.DeviceIndexFragment
import com.rokid.mobile.sdk.demo.other.OtherIndexFragment
import com.rokid.mobile.sdk.demo.skill.SkillIndexFragment
import kotlinx.android.synthetic.main.demo_activity_index.*


/**
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SDKDemoActivity : BaseActivity() {

    private lateinit var fragmentList: Array<Fragment>
    private lateinit var tabTitle: List<String>
    private lateinit var tabIcon: List<String>

    override fun layoutId(): Int {
        return R.layout.demo_activity_index
    }

    override fun initVariable() {
        fragmentList = arrayOf(AccountIndexFragment(),
                DeviceIndexFragment(),
                SkillIndexFragment(),
                OtherIndexFragment())

        tabTitle = listOf("帐号", "设备", "技能", "更多")

        tabIcon = listOf(getString(R.string.icon_account), getString(R.string.icon_device), getString(R.string.icon_skill), getString(R.string.icon_other))
    }

    override fun initViews() {
    }

    override fun initListeners() {
        demo_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position != 0 && TextUtils.isEmpty(RokidMobileSDK.account.token)) {
                    AlertDialog.Builder(this@SDKDemoActivity)
                            .setPositiveButton("知道了", null)
                            .setMessage("请先登录，才可以使用其他功能哟。")
                            .show()

                    demo_tab_layout.getTabAt(0)?.select()
                    return
                }

                onTabItemSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        val mLayoutInflater = this.layoutInflater

        for (position in 0 until fragmentList.size) {
            val tab = demo_tab_layout.newTab()
            val view = mLayoutInflater.inflate(R.layout.demo_tab_item, null)
            tab.customView = view

            val icon = view.findViewById(R.id.demo_tab_icon) as IconTextView
            icon.text = tabIcon[position]

            val text = view.findViewById(R.id.demo_tab_title) as TextView
            text.text = tabTitle[position]

            demo_tab_layout.addTab(tab)
        }
    }

    private fun onTabItemSelected(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.demo_container, fragmentList[position]).commit()
    }

}



