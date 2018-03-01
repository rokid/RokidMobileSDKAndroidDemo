package com.rokid.mobile.sdk.demo.java.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rokid.mobile.lib.base.util.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BaseFragmentPresenter> extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected P mPresenter;

    private View rootView;

    private Unbinder mButterKnifeBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, this.getClass().getSimpleName());
        if (rootView == null) {
            // 初始化布局
            rootView = inflater.inflate(getLayoutId(), null);
            // 绑定 ButterKnife
            mButterKnifeBinder = ButterKnife.bind(this, rootView);
            // 初始化变量
            initVariables(rootView, container, savedInstanceState);
            // 初始化监听
            initListeners();
            // 初始化P层
            mPresenter = initPresenter();
            if (null != mPresenter) {
                Log.i(TAG, "Presenter: " + mPresenter.getClass().getSimpleName());
                mPresenter.onCreateView();
            }
        }

        return rootView;
    }

    // 布局资源ID
    protected abstract int getLayoutId();

    // 初始化P层
    protected abstract P initPresenter();

    // 初始化变量 子类需要就重写
    protected abstract void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState);

    // 初始化监听
    protected abstract void initListeners();

    public Intent getIntent() {
        Log.d(TAG, this.getClass().getSimpleName());
        return getActivity().getIntent();
    }

    public P getPresenter() {
        Log.d(TAG, this.getClass().getSimpleName());
        return mPresenter;
    }

    public void showToastShort(final CharSequence text) {
        Logger.i(this.getClass().getSimpleName() + " The toast context: " + text);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {

        if (null != mPresenter) {
            Logger.i("Presenter: " + mPresenter.getClass().getSimpleName());
            mPresenter.onDestroy();
            mPresenter = null;
        }

        rootView = null;
        if (null != mButterKnifeBinder) {
            mButterKnifeBinder.unbind();
        }

        super.onDestroy();
    }
}
