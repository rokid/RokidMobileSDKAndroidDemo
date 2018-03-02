package com.rokid.mobile.sdk.demo.device

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.rokid.mobile.lib.base.util.CollectionUtils
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.device.RKDevice
import com.rokid.mobile.lib.entity.event.device.EventDeviceSysUpdate
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback
import com.rokid.mobile.lib.xbase.device.callback.IUnbindDeviceCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.TypeConstants
import com.rokid.mobile.sdk.demo.base.adapter.data.ActionData
import com.rokid.mobile.sdk.demo.base.adapter.item.ActionItem
import com.rokid.mobile.sdk.demo.base.adapter.item.DeviceItem
import com.rokid.mobile.sdk.demo.base.adapter.item.UnbindItem
import com.rokid.mobile.ui.recyclerview.adapter.BaseRVAdapter
import com.rokid.mobile.ui.recyclerview.item.BaseItem
import kotlinx.android.synthetic.main.device_fragment_list.*
import kotlinx.android.synthetic.main.device_fragment_list.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by wangshuwen on 2017/12/4.
 */
class DeviceListFragment : BaseFragment() {

    lateinit var deviceBtn: Button
    lateinit var progressBar: ProgressBar
    lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: BaseRVAdapter<DeviceItem>

    override fun layoutId(): Int = R.layout.device_fragment_list

    override fun initViews() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        deviceBtn = rootView!!.fragment_device_list_btn
        progressBar = rootView!!.fragment_device_list_pb
        mRecycler = rootView!!.fragment_device_list_rv

        mRecycler.apply {
            layoutManager = LinearLayoutManager(activity as Context?)
            mAdapter = BaseRVAdapter()
            adapter = mAdapter
        }
    }

    override fun initListeners() {
        deviceBtn.setOnClickListener {
            getDeviceItemList()
        }

        mAdapter.setOnItemViewClickListener { deviceItem, sectionKey, sectionItemPosition ->
            Logger.i("onItemClick position=" + sectionItemPosition + " deviceId=" + deviceItem.data.rokiId)
            toast(deviceItem.data.toString())
        }
    }

    fun getDeviceItemList() {
        progressBar.visibility = View.VISIBLE
        mRecycler.visibility = View.GONE
        mAdapter.clearAllItemView()
        RokidMobileSDK.device.getDeviceList(object : IGetDeviceListCallback {
            override fun onGetDeviceListFailed(p0: String?, p1: String?) {
                toast("获取设备列表失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""))
            }

            override fun onGetDeviceListSucceed(deviceList: MutableList<RKDevice>?) {
                progressBar.visibility = View.GONE
                if (CollectionUtils.isEmpty(deviceList)) {
                    toast("设备列表为空")
                    mRecycler.visibility = View.GONE
                    return
                }

                val deviceItemList = ArrayList<DeviceItem>()
                deviceList!!.forEachIndexed { index, rkDevice ->
                    deviceItemList.add(DeviceItem(rkDevice, {
                        RokidMobileSDK.device.unbindDevice(rkDevice.rokiId!!, object : IUnbindDeviceCallback {
                            override fun onUnbindDeviceSucceed() {
                                toast("解绑设备成功")
                                mAdapter.removeItemView(index)
                                mAdapter.notifyDataSetChanged()
                            }

                            override fun onUnbindDeviceFailed(p0: String?, p1: String?) {
                                toast("解绑设备失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""))
                            }
                        })
                    }))
                }

                mAdapter.setItemViewList(deviceItemList)
                mRecycler.visibility = View.VISIBLE
            }
        })
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