package com.rokid.mobile.sdk.demo.base.item

import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.ui.recyclerview.item.BaseItem
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder
import kotlinx.android.synthetic.main.item_card.view.*

/**
 * @author hongquan.zhao
 * Create Date:2018/3/21
 * Version:V1.0
 */

class CardItem(data: CardMsgBean) : BaseItem<CardMsgBean>(data) {

    val DeviceItem_ITEM_TYPE = 0

    override fun getViewType(): Int = DeviceItem_ITEM_TYPE

    override fun getLayoutId(viewType: Int): Int = R.layout.item_card

    override fun onReleaseViews(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder!!.itemView.apply {
            fragment_msg_id.text = ""
            fragment_card_content.text = ""
        }
    }

    override fun onSetViewsData(holder: BaseViewHolder?, sectionKey: Int, sectionViewPosition: Int) {
        holder!!.itemView.apply {
            fragment_msg_id.text = data.msgId
            fragment_card_content.text = data.msgTxt
        }
    }

}