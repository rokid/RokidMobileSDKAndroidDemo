package com.rokid.mobile.sdk.demo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by wangshuwen on 2017/12/3.
 */
class MyFragmentPagerAdapter(fm: FragmentManager,var fragmentList:List<Node>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment=fragmentList[position].fragment

    override fun getCount(): Int = fragmentList.count()

    override fun getPageTitle(position: Int): CharSequence= fragmentList[position].title

    companion object {
        class Node(var title: String,var fragment: Fragment)
    }


}