package com.rokid.mobile.sdk.demo.base.adapter.item

import android.view.View
import android.widget.Toast
import com.rokid.mobile.lib.entity.bean.device.RKDevice
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.adapter.TypeConstants
import com.rokid.mobile.sdk.demo.base.adapter.data.ActionData
import com.rokid.mobile.ui.recyclerview.item.BaseItem
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder
import kotlinx.android.synthetic.main.item_action.view.*

/**
 * Created by wangshuwen on 2017/12/3.
 */
class ActionItem(data: ActionData) : BaseItem<ActionData>(data) {


    val TYPE_NORMAL = 1

    override fun getViewType(): Int = TYPE_NORMAL

    override fun getLayoutId(viewType: Int): Int = R.layout.item_action

    override fun onReleaseViews(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder?.let {
            it.itemView.action_text.apply {
                text = ""
                visibility = View.GONE
                setOnClickListener {

                }
            }
        }


    }

    override fun onSetViewsData(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder?.let {
            it.itemView.action_text.apply {
                visibility = View.VISIBLE
                text = data.action
                setOnClickListener {
                    when (data.type) {
                        TypeConstants.GET_DEVICE_LIST -> getDeviceList()
                    }
                }
            }
        }
    }

    /**
     * 获取设备按钮
     */
    fun getDeviceList() {

        RokidMobileSDK.device.getDeviceList(object : IGetDeviceListCallback {
            override fun onGetDeviceListSucceed(p0: MutableList<RKDevice>?) {

                RokidMobileSDK.device.getVersionInfo(p0!![0].rokiId)

                holder.itemView.post {
                    Toast.makeText(holder.context, "获取设备列表成功 " + p0.let { p0.toString() }, Toast.LENGTH_LONG).show()
                }


            }

            override fun onGetDeviceListFailed(p0: String?, p1: String?) {
                holder.itemView.post {
                    Toast.makeText(holder.context, "获取设备列表失败 errorCode=" + (p0 ?: "") + "erroMsg= " + (p1 ?: ""), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }


}

