package com.rokid.mobile.sdk.demo.fragment

import com.rokid.lib_appbase.recyclerview.item.BaseItem
import com.rokid.mobile.sdk.demo.adapter.TypeConstants
import com.rokid.mobile.sdk.demo.adapter.data.ActionData
import com.rokid.mobile.sdk.demo.adapter.item.ActionItem
import com.rokid.mobile.sdk.demo.adapter.item.UnbindItem
import java.util.*

/**
 * Created by wangshuwen on 2017/12/4.
 */
class DeviceFragment : BaseFragment(){


    override fun getItemList(): List<BaseItem<Any>> {

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

}