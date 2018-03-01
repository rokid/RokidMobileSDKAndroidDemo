package com.rokid.mobile.sdk.demo.java.base;

import android.content.Intent;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Fragment 业务逻辑处理基类
 *
 * @param <F> 代表Fragment的泛型
 */
public abstract class BaseFragmentPresenter<F extends BaseFragment> {

    private static final String TAG = BaseFragmentPresenter.class.getSimpleName();

    private WeakReference<F> mFragmentWeak;

    protected boolean isLogin;

    public BaseFragmentPresenter(F fragment) {
        Log.d(TAG, "DeviceBindFragment: " + (null != fragment ? fragment.getClass().getSimpleName() : "Null"));
        // 绑定 Fragment
        this.mFragmentWeak = new WeakReference<F>(fragment);
    }

    public void onCreateView() {
        Log.d(TAG, this.getClass().getSimpleName());
        onLoadData();
    }

    protected void onStart() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    protected void onResume() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    protected void onUserVisible() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    protected void onUserInvisible() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    protected void onPause() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    protected void onStop() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    protected void onDestroyView() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    /**
     * 解除全部绑定
     */
    public void onDestroy() {
        Log.d(TAG, "UnBindFragment: " + (null != mFragmentWeak.get()
                ? mFragmentWeak.get().getClass().getSimpleName() : "Null"));
        mFragmentWeak.clear();
        mFragmentWeak = null;
    }

    public void onLoadData() {
        Log.d(TAG, this.getClass().getSimpleName());
    }

    public Intent getIntent() {
        return mFragmentWeak.get() != null ? mFragmentWeak.get().getIntent() : null;
    }

    /**
     * 获取绑定的 Fragment
     */
    public F getFragment() {
        Log.d(TAG, "DeviceBindFragment: " + (null != mFragmentWeak.get()
                ? mFragmentWeak.get().getClass().getSimpleName() : "Null"));
        return mFragmentWeak.get();
    }

    /**
     * 检测是否 View 已经绑定
     */
    public boolean isFragmentBind() {
        Log.d(TAG, this.getClass().getSimpleName() + " ,Check the Fragment isBind？ "
                + (mFragmentWeak != null && mFragmentWeak.get() != null));
        return mFragmentWeak != null && mFragmentWeak.get() != null;
    }


}
