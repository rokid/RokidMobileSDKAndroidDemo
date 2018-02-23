package com.rokid.mobile.sdk.demo.adapter.item

import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.rokid.mobile.lib.xbase.device.callback.IUnbindDeviceCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.adapter.data.ActionData
import com.rokid.mobile.ui.recyclerview.item.BaseItem
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder

/**
 * Created by wangshuwen on 2017/12/3.
 */

class UnbindItem(data: ActionData) : BaseItem<ActionData>(data) {

    val TYPE_UNBIND = 2

    lateinit var deviceEt: EditText

    lateinit var txt: TextView

    override fun getViewType(): Int = TYPE_UNBIND

    override fun getLayoutId(viewType: Int): Int = R.layout.item_unbind

    override fun onReleaseViews(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {

    }

    override fun onSetViewsData(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        deviceEt = holder!!.itemView.findViewById(R.id.item_unbind_et)
        txt = holder.itemView.findViewById(R.id.item_unbind_txt)
        txt.text = data.action

        txt.setOnClickListener {
            RokidMobileSDK.device.unbindDevice(deviceEt.text.trim().toString(), object : IUnbindDeviceCallback {
                        override fun onUnbindDeviceSucceed() {
                            Toast.makeText(holder.context, "解绑成功", Toast.LENGTH_LONG).show()
                        }

                        override fun onUnbindDeviceFailed(p0: String?, p1: String?) {
                            Toast.makeText(holder.context,
                                    "解绑失败 errorCode=" + (p0 ?: "") + "erroMsg= " + (p1 ?: ""),
                                    Toast.LENGTH_SHORT).show()
                        }

                    })
        }
    }

}