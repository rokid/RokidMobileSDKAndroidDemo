package com.rokid.mobile.sdk.demo.java.other;

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

public class OtherIndexFragment extends BaseFragment {

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
        List<BaseFragmentAdapter.Node> moreList = new ArrayList<>();
        moreList.add(new BaseFragmentAdapter.Node("消息", new OtherMessageFragment()));
        moreList.add(new BaseFragmentAdapter.Node("card列表", new CardListFragment()));
        moreList.add(new BaseFragmentAdapter.Node("ASR纠错", new AsrFragment()));
        BaseFragmentAdapter mAdapter = new BaseFragmentAdapter(getFragmentManager(), moreList);

        moreVp.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
    }
}
