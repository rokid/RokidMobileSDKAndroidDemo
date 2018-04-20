package com.rokid.mobile.sdk.demo.java.other;

import android.support.annotation.NonNull;

import com.rokid.mobile.lib.entity.bean.home.AsrErrorCorrectBean;
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean;
import com.rokid.mobile.lib.xbase.home.callback.IGetAsrErrorCallback;
import com.rokid.mobile.lib.xbase.home.callback.IGetCardsCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

import java.util.List;

/**
 * Created by hongquan.zhao on 2018/03/21.
 */

public class AsrPresenter extends BaseFragmentPresenter<AsrFragment> {

    private static final String TAG = AsrPresenter.class.getSimpleName();

    public AsrPresenter(AsrFragment fragment) {
        super(fragment);
    }


    public void asrCorrectCreate(@NonNull String originText, @NonNull String targetText) {
        RokidMobileSDK.vui.asrCorrectCreate(originText, targetText, new IGetAsrErrorCallback() {

            @Override
            public void onGetAsrErrorSucceed(AsrErrorCorrectBean asrErrorCorrectBean) {
                getFragment().showToast("纠错成功 AsrErrorCorrectBean = " + asrErrorCorrectBean.toString());
            }

            @Override
            public void onGetAsrErrorFailed(String s, String s1) {
                getFragment().showToast("纠错失败 errorCode = " + s + " , errorMsg =  " + s1);
            }
        });


    }
}
