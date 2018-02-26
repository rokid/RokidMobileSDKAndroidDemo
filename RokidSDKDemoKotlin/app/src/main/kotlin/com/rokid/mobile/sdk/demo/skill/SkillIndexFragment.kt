package com.rokid.mobile.sdk.demo.skill

import android.content.Intent
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.skill_fragment_index.view.*

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
class SkillIndexFragment : BaseFragment() {
    override fun layoutId(): Int {
        return R.layout.skill_fragment_index
    }

    override fun initViews() {
    }

    override fun initListeners() {
        rootView!!.skill_alarm_btn.setOnClickListener {
            val intent = Intent(activity, SkillAlarmActivity::class.java)
            startActivity(intent)
        }

        rootView!!.skill_homebase_btn.setOnClickListener {
            val intent = Intent(activity, SkillHomebaseActivity::class.java)
            startActivity(intent)
        }
    }

}