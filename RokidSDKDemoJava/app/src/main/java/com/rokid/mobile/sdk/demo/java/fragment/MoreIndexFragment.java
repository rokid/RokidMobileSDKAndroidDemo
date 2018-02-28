package com.rokid.mobile.sdk.demo.java.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.adapter.BaseFragmentAdapter;
import com.rokid.mobile.sdk.demo.java.presenter.BaseFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/28.
 */

public class MoreIndexFragment extends BaseFragment {

    @BindView(R.id.other_tab_layout)
    TabLayout moreTab;

    @BindView(R.id.other_viewPager)
    ViewPager moreVp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more_index;
    }

    @Override
    protected BaseFragmentPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreTab.setupWithViewPager(moreVp);
        List<BaseFragmentAdapter.Node> accountList = new ArrayList<>();
        accountList.add(new BaseFragmentAdapter.Node("消息", new MoreMessageFragment()));
        BaseFragmentAdapter mAdapter = new BaseFragmentAdapter(getFragmentManager(), accountList);

        moreVp.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
    }
}
