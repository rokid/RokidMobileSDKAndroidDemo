package com.rokid.mobile.sdk.demo.java.skill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.base.BaseFragment;
import com.rokid.mobile.sdk.demo.java.util.DisplayUtils;
import com.rokid.mobile.sdk.demo.java.util.webkit.DemoWebView;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/28.
 */

public class SkillHomebaseFragment extends BaseFragment<SkillHomebaseFragmentPresenter> {

    @BindView(R.id.skill_homebase_webview)
    DemoWebView webview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_skill_homebase;
    }

    @Override
    protected SkillHomebaseFragmentPresenter initPresenter() {
        return new SkillHomebaseFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {}

    @Override
    public void onResume() {
        super.onResume();
        webview.loadUrl("https://s.rokidcdn.com/homebase/himalaya/pre/index.html#/homes/index");
    }
}
