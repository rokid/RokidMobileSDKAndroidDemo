package com.rokid.mobile.sdk.demo.device

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.event.device.EventDeviceSysUpdate
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.TypeConstants
import com.rokid.mobile.sdk.demo.base.adapter.data.ActionData
import com.rokid.mobile.sdk.demo.base.adapter.item.ActionItem
import com.rokid.mobile.sdk.demo.base.adapter.item.UnbindItem
import com.rokid.mobile.ui.recyclerview.adapter.BaseRVAdapter
import com.rokid.mobile.ui.recyclerview.item.BaseItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * Created by wangshuwen on 2017/12/4.
 */
class DeviceListFragment : BaseFragment() {

    lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: BaseRVAdapter<BaseItem<Any>>

    override fun layoutId(): Int = R.layout.device_fragment_list

    override fun initViews() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        mRecycler = rootView!!.findViewById(R.id.fragment_common_rv)

        mRecycler.apply {
            layoutManager = LinearLayoutManager(activity as Context?)
            mAdapter = BaseRVAdapter()
            adapter = mAdapter
        }

        mAdapter.setItemViewList(getItemList())
    }

    override fun initListeners() {
    }

    fun getItemList(): List<BaseItem<Any>> {

        val itemList = ArrayList<BaseItem<Any>>()

        val actionList = listOf(
                ActionData("获取设备列表", TypeConstants.GET_DEVICE_LIST),
                ActionData("解绑设备", TypeConstants.UNBIND))

        actionList.forEach {
            when (it.type) {
                TypeConstants.UNBIND -> itemList.add(UnbindItem(it) as BaseItem<Any>)
                else -> itemList.add(ActionItem(it) as BaseItem<Any>)
            }
        }
        return itemList
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSysInfo(eventDeviceSysUpdate: EventDeviceSysUpdate) {
        Logger.e("onSysInfo eventDeviceSysUpdate" + eventDeviceSysUpdate.toString())
    }

}