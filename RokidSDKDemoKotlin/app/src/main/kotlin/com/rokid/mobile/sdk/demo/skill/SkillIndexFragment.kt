package com.rokid.mobile.sdk.demo.skill

import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.skill_fragment_index.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillIndexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.skill_fragment_index

    override fun initViews() {
        rootView!!.skill_title.text = "技能"
        rootView!!.skill_subtitle.text = "SDK Skill Demo"

        rootView!!.skill_tab_layout.setupWithViewPager(rootView!!.skill_viewPager)
        rootView!!.skill_viewPager.adapter = BaseFragmentAdapter(activity!!.supportFragmentManager,
                SkillData.fragmentList)
    }

    override fun initListeners() {
    }

    object SkillData {
        val fragmentList = listOf(
                BaseFragmentAdapter.Companion.Node("闹钟", SkillAlarmFragment()),
                BaseFragmentAdapter.Companion.Node("提醒", SkillRemindFragment()),
                BaseFragmentAdapter.Companion.Node("媒体内容", SkillMediaFragment()),
                BaseFragmentAdapter.Companion.Node("智能家居", SkillHomebaseFragment()),
                BaseFragmentAdapter.Companion.Node("技能商店", SkillStoreFragment()),
                BaseFragmentAdapter.Companion.Node("酷狗登录", SkillKuGouFragment())
        )
    }

}