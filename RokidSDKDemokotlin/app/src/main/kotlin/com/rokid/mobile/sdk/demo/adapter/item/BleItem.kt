package com.rokid.mobile.sdk.demo.adapter.item

import android.view.View
import com.rokid.lib_appbase.recyclerview.item.BaseItem
import com.rokid.lib_appbase.recyclerview.item.BaseViewHolder
import com.rokid.mobile.lib.entity.bean.bluetooth.BTDeviceBean
import com.rokid.mobile.sdk.demo.R
import kotlinx.android.synthetic.main.item_ble.view.*

/**
 * Created by wangshuwen on 2017/12/8.
 */
class BleItem(data: BTDeviceBean) : BaseItem<BTDeviceBean>(data) {

    val BLE_ITEM_TYPE = 0

    override fun getViewType(): Int = BLE_ITEM_TYPE

    override fun getLayoutId(viewType: Int): Int = R.layout.item_ble

    override fun onReleaseViews(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder!!.itemView.apply {
            ble_item_btName_txt.text = ""
            ble_item_progress.visibility = View.GONE
        }
    }

    override fun onSetViewsData(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder!!.itemView.apply {
            ble_item_btName_txt.text = data.name
        }
    }


    fun showProgress() {
        if (null == holder.itemView.ble_item_progress) {
            return
        }
        holder.itemView.ble_item_progress.visibility=View.VISIBLE
    }

    fun hideProgress() {
        if (null == holder.itemView.ble_item_progress) {
            return
        }
        holder.itemView.ble_item_progress.visibility=View.GONE
    }

}