package com.rokid.mobile.sdk.demo.other

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.rokid.mobile.lib.base.util.CollectionUtils
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean
import com.rokid.mobile.lib.xbase.home.callback.IGetCardsCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import com.rokid.mobile.sdk.demo.base.item.CardItem
import com.rokid.mobile.ui.recyclerview.adapter.BaseRVAdapter
import kotlinx.android.synthetic.main.device_fragment_list.view.*

/**
 * @author hongquan.zhao
 * Create Date:2018/3/21
 * Version:V1.0
 */
class CardListFragment : BaseFragment() {
    lateinit var carListBtn: Button
    lateinit var mRecycler: RecyclerView;
    lateinit var mAdapter: BaseRVAdapter<CardItem>;


    override fun layoutId(): Int = R.layout.device_fragment_list;


    override fun initViews() {
        carListBtn = rootView!!.fragment_device_list_btn
        carListBtn.setText(getString(R.string.fragment_card_list_action))
        mRecycler = rootView!!.fragment_device_list_rv

        mRecycler.apply {
            layoutManager = LinearLayoutManager(activity as Context?)
            mAdapter = BaseRVAdapter()
            adapter = mAdapter
        }
    }

    override fun initListeners() {
        carListBtn.setOnClickListener {
            mRecycler.visibility = View.GONE
            mAdapter.clearAllItemView()
            getDeviceList(0);
        }

    }

    fun getDeviceList(maxDbId: Int) {
        RokidMobileSDK.vui.getCardList(maxDbId, object : IGetCardsCallback {
            override fun onGetCardsSucceed(list: List<CardMsgBean>, var2: Boolean) {
                setDeviceListData(list)
            }

            override fun onGetCardsFailed(s: String, s1: String) {
                toast(getString(R.string.fragment_card_list_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1)
            }
        })
    }

    fun setDeviceListData(itemList: List<CardMsgBean>) {
        if (CollectionUtils.isEmpty(itemList)) {
            toast(getString(R.string.fragment_card_list_empty));
            mRecycler.setVisibility(View.GONE);
        }

        var cardItemList = ArrayList<CardItem>();
        itemList!!.forEachIndexed { index, card ->
            cardItemList.add(CardItem(card))
        }
        mAdapter.setItemViewList(cardItemList);
        mRecycler.setVisibility(View.VISIBLE);
    }

}
