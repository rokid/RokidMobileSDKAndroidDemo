package com.rokid.mobile.sdk.demo.java.device;

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

public class DeviceIndexFragment extends BaseFragment {

    @BindView(R.id.device_tab_layout)
    TabLayout deviceTab;

    @BindView(R.id.device_viewPager)
    ViewPager deviceVp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_device_index;
    }

    @Override
    protected BaseFragmentPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        deviceTab.setupWithViewPager(deviceVp);
        List<BaseFragmentAdapter.Node> deviceList = new ArrayList<>();
        deviceList.add(new BaseFragmentAdapter.Node("绑定", new DeviceBindFragment()));
        deviceList.add(new BaseFragmentAdapter.Node("设备列表", new DeviceListFragment()));
        BaseFragmentAdapter mAdapter = new BaseFragmentAdapter(getFragmentManager(), deviceList);

        deviceVp.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
    }
}
