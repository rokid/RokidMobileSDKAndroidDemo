package com.rokid.mobile.sdk.demo.java.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.entity.bean.remotechannel.CardMsgBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.presenter.MoreMessageFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class MoreMessageFragment extends BaseFragment<MoreMessageFragmentPresenter> {
    @BindView(R.id.fragment_message_info_txt)
    TextView infoTxt;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected MoreMessageFragmentPresenter initPresenter() {
        return new MoreMessageFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initListeners() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedCard(CardMsgBean cardMsgBean) {
        Logger.d("onSysInfo eventDeviceSysUpdate" + cardMsgBean.toString());
        if (!TextUtils.isEmpty(infoTxt.getText())) {
            infoTxt.setText("");
        }
        infoTxt.setText(cardMsgBean.getMsgTxt());
    }
}
