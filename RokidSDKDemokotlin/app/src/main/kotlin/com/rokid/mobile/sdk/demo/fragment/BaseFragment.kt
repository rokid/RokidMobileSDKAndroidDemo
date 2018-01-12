package com.rokid.mobile.sdk.demo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rokid.lib_appbase.recyclerview.adapter.BaseRVAdapter
import com.rokid.lib_appbase.recyclerview.item.BaseItem
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.event.device.EventDeviceSysUpdate
import com.rokid.mobile.sdk.demo.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by wangshuwen on 2017/12/3.
 */
abstract class BaseFragment : Fragment() {

    lateinit var rv: RecyclerView

    lateinit var mAdapter: BaseRVAdapter<BaseItem<Any>>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        EventBus.getDefault().register(this)
        var rootView = inflater!!.inflate(R.layout.fragment_common, container!!, false)
        rv = rootView.findViewById(R.id.fragment_common_rv)
        initRv()
        initdata()
        return rootView
    }


    private fun initRv() {
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            mAdapter = BaseRVAdapter()
            adapter = mAdapter
        }
    }

    private fun initdata() {
        mAdapter.setItemViewList(getItemList())
    }

    abstract fun getItemList(): List<BaseItem<Any>>


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe
    fun onSysInfo(eventDeviceSysUpdate: EventDeviceSysUpdate) {
        Logger.e("onSysInfo eventDeviceSysUpdate" + eventDeviceSysUpdate.toString())
    }
}