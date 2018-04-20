package com.rokid.mobile.sdk.demo.java.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rokid.mobile.lib.base.util.CollectionUtils;
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;
import com.rokid.mobile.sdk.demo.java.base.item.CardItem;
import com.rokid.mobile.ui.recyclerview.adapter.BaseRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hongquan.zhao on 2018/3/21.
 *
 * @decription:获取card列表
 */

public class AsrFragment extends BaseFragment<AsrPresenter> {
    @BindView(R.id.fragment_device_list_btn)
    Button carListBtn;

    @BindView(R.id.fragment_device_list_rv)
    RecyclerView deviceRv;


    private BaseRVAdapter<CardItem> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_device;
    }

    @Override
    protected AsrPresenter initPresenter() {
        return new AsrPresenter(this);
    }


    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        carListBtn.setText("ASR纠错");
        mAdapter = new BaseRVAdapter<>();
        deviceRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        deviceRv.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        carListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceRv.setVisibility(View.GONE);
                mAdapter.clearAllItemView();
                getPresenter().asrCorrectCreate("东冯破", "东风破");
            }
        });

    }

    public void showToast(String text) {
        showToastShort(text);
    }

//    public void setDeviceListData(List<CardMsgBean> itemList) {
//        if (CollectionUtils.isEmpty(itemList)) {
//            showToast(getString(R.string.fragment_card_list_empty));
//            deviceRv.setVisibility(View.GONE);
//            return;
//        }
//
//        List<CardItem> deviceItemList = new ArrayList<>();
//        for (CardMsgBean rkDevice : itemList) {
//            CardItem deviceItem = new CardItem(rkDevice);
//            deviceItemList.add(deviceItem);
//        }
//        mAdapter.setItemViewList(deviceItemList);
//        deviceRv.setVisibility(View.VISIBLE);
//    }


}
