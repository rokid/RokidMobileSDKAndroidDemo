package com.rokid.mobile.sdk.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rokid.mobile.sdk.demo.adapter.MyFragmentPagerAdapter
import com.rokid.mobile.sdk.demo.fragment.BindFragment
import com.rokid.mobile.sdk.demo.fragment.DeviceFragment
import com.rokid.mobile.sdk.demo.fragment.EventFragment
import com.rokid.mobile.sdk.demo.fragment.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentNodeList = listOf(
                MyFragmentPagerAdapter.Companion.Node("登录", LoginFragment()),
                MyFragmentPagerAdapter.Companion.Node("设备", DeviceFragment()),
                MyFragmentPagerAdapter.Companion.Node("配网", BindFragment()),
                MyFragmentPagerAdapter.Companion.Node("消息", EventFragment()))

        main_tablayer.setupWithViewPager(viewpage)

        viewpage.adapter = MyFragmentPagerAdapter(supportFragmentManager, fragmentNodeList)

    }

}



