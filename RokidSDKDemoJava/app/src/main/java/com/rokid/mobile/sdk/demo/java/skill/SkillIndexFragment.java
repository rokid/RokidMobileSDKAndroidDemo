package com.rokid.mobile.sdk.demo.java.skill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.adapter.BaseFragmentAdapter;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/28.
 */

public class SkillIndexFragment extends BaseFragment {

    @BindView(R.id.skill_tab_layout)
    TabLayout skillTab;

    @BindView(R.id.skill_viewPager)
    ViewPager skillVp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_skill_index;
    }

    @Override
    protected BaseFragmentPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        skillTab.setupWithViewPager(skillVp);
        List<BaseFragmentAdapter.Node> skillList = new ArrayList<>();
        skillList.add(new BaseFragmentAdapter.Node("闹钟", new SkillAlarmFragment()));
        skillList.add(new BaseFragmentAdapter.Node("提醒", new SkillRemindFragment()));
        skillList.add(new BaseFragmentAdapter.Node("智能家居", new SkillHomebaseFragment()));
        BaseFragmentAdapter mAdapter = new BaseFragmentAdapter(getFragmentManager(), skillList);

        skillVp.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
    }
}
