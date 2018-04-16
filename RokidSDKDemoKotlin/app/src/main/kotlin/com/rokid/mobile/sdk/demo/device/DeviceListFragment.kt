package com.rokid.mobile.sdk.demo.device

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.rokid.mobile.lib.base.util.CollectionUtils
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.event.device.EventDeviceStatus
import com.rokid.mobile.lib.entity.event.device.EventDeviceSysUpdate
import com.rokid.mobile.lib.xbase.device.callback.IPingDeviceCallback
import com.rokid.mobile.lib.xbase.device.callback.IUnbindDeviceCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.bean.SDKDevice
import com.rokid.mobile.sdk.callback.IGetDeviceListCallback
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.adapter.item.DeviceItem
import com.rokid.mobile.ui.recyclerview.adapter.BaseRVAdapter
import kotlinx.android.synthetic.main.device_fragment_list.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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
            if (deviceItem == null) {
                return@setOnItemViewClickListener
            }
            Logger.i("onItemClick position=" + sectionItemPosition + " deviceId=" + deviceItem.data.deviceId)
            toast(deviceItem.data.toString())
//            getDeviceStatus(deviceItem.data)
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

            override fun onGetDeviceListSucceed(deviceList: MutableList<SDKDevice>?) {
                progressBar.visibility = View.GONE
                if (CollectionUtils.isEmpty(deviceList)) {
                    toast("设备列表为空")
                    mRecycler.visibility = View.GONE
                    return
                }

                val deviceItemList = ArrayList<DeviceItem>()
                deviceList!!.forEachIndexed { index, rkDevice ->
                    deviceItemList.add(DeviceItem(rkDevice, {
                        RokidMobileSDK.device.unbindDevice(rkDevice.deviceId!!, object : IUnbindDeviceCallback {
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

    fun getDeviceStatus(sddDevice: SDKDevice) {
        RokidMobileSDK.device.pingDevice(sddDevice, object : IPingDeviceCallback {
            override fun onSuccess(p0: EventDeviceStatus?) {
                toast("获取设备状态成功，" + p0.toString())
            }

            override fun onFailed(p0: String?, p1: String?) {
                toast("获取设备状态失败，errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""))
            }
        })
    }

}