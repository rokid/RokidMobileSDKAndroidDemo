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
import com.rokid.mobile.sdk.demo.base.adapter.TypeConstants
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
class DeviceIndexFragment : Fragment() {

    private var rootView: View? = null
    lateinit var rv: RecyclerView
    lateinit var mAdapter: BaseRVAdapter<BaseItem<Any>>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        if (null == rootView) {
            rootView = inflater!!.inflate(R.layout.device_fragment_index, container!!, false)
            rv = rootView!!.findViewById(R.id.fragment_common_rv)
            initRv()
            initData()
        }

        return rootView
    }


    private fun initRv() {
        rv.apply {
            layoutManager = LinearLayoutManager(activity as Context?)
            mAdapter = BaseRVAdapter()
            adapter = mAdapter
        }
    }

    private fun initData() {
        mAdapter.setItemViewList(getItemList())
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