package com.rokid.mobile.sdk.demo.java.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentAdapter;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/28.
 */

public class AccountIndexFragment extends BaseFragment {

    @BindView(R.id.account_tab_layout)
    TabLayout accountTab;

    @BindView(R.id.account_viewPager)
    ViewPager accountVp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_index;
    }

    @Override
    protected BaseFragmentPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        accountTab.setupWithViewPager(accountVp);
        List<BaseFragmentAdapter.Node> accountList = new ArrayList<>();
        accountList.add(new BaseFragmentAdapter.Node("登录", new AccountLoginFragment()));
        BaseFragmentAdapter mAdapter = new BaseFragmentAdapter(getFragmentManager(), accountList);

        accountVp.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {

    }


}
