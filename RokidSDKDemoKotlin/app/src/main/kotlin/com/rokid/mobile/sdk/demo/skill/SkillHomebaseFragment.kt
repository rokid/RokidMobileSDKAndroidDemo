package com.rokid.mobile.sdk.demo.skill

import android.view.MotionEvent
import android.view.View
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.util.DisplayUtils
import kotlinx.android.synthetic.main.skill_fragment_homebase.*
import kotlinx.android.synthetic.main.skill_fragment_homebase.view.*


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
        val dm = DisplayUtils.getDisplayMetrics(activity)
        rootView!!.skill_homebase_webview.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val point = event.x
                        if (point > 0 && point < 50 || point > dm.widthPixels - 50 && point < dm.widthPixels) {
                            rootView!!.skill_homebase_webview.requestDisallowInterceptTouchEvent(false)
                        } else {
                            rootView!!.skill_homebase_webview.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                }

                return false
            }

        })
    }

    override fun onResume() {
        super.onResume()
//        skill_homebase_webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/dev/index.html#/homes/index")
        skill_homebase_webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/pre/index.html#/homes/index")
    }

}