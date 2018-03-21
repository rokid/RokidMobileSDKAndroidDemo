package com.rokid.mobile.sdk.demo.java.other;

import com.rokid.mobile.lib.entity.bean.device.RKDevice;
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean;
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback;
import com.rokid.mobile.lib.xbase.home.callback.IGetCardsCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

import java.util.List;

/**
 * Created by hongquan.zhao on 2018/03/21.
 */

public class CardPresenter extends BaseFragmentPresenter<CardListFragment> {

    private static final String TAG = CardPresenter.class.getSimpleName();

    public CardPresenter(CardListFragment fragment) {
        super(fragment);
    }


    public void getDeviceList(int maxDbId) {
        RokidMobileSDK.vui.getCardList(maxDbId, new IGetCardsCallback() {
            @Override
            public void onGetCardsSucceed(List<CardMsgBean> list, boolean var2) {
                getFragment().setDeviceListData(list);
            }

            @Override
            public void onGetCardsFailed(String s, String s1) {
                getFragment().showToast(getFragment().getString(R.string.fragment_device_list_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
            }
        });
    }
}
