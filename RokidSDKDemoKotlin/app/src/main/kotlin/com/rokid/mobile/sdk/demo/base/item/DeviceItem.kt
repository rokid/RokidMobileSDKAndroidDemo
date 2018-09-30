package com.rokid.mobile.sdk.demo.base.adapter.item

import android.view.View
import com.rokid.mobile.sdk.bean.SDKDevice
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.ui.recyclerview.item.BaseItem
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder
import kotlinx.android.synthetic.main.device_item_list.view.*

typealias Unbind = () -> Unit
typealias Ping = () -> Unit

class DeviceItem(data: SDKDevice, var ping: Ping, var unbind: Unbind) : BaseItem<SDKDevice>(data) {

    val DeviceItem_ITEM_TYPE = 0

    override fun getViewType(): Int = DeviceItem_ITEM_TYPE

    override fun getLayoutId(viewType: Int): Int = R.layout.device_item_list

    override fun onReleaseViews(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder!!.itemView.apply {
            fragment_device_id.text = ""
            fragment_device_id.visibility = View.GONE
            fragment_device_unbind.visibility = View.GONE
            fragment_device_ping.visibility = View.GONE
        }
    }

    override fun onSetViewsData(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder!!.itemView.apply {
            fragment_device_id.text = data.deviceId
            fragment_device_id.visibility = View.VISIBLE

            // Ping
            fragment_device_ping.visibility = View.VISIBLE
            fragment_device_ping.setOnClickListener{ ping() }

            // Unbind
            fragment_device_unbind.visibility = View.VISIBLE
            fragment_device_unbind.setOnClickListener { unbind() }
        }
    }
}