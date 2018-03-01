package com.rokid.mobile.sdk.demo.java.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.presenter.AccountLoginFragmentPresenter;
import com.rokid.mobile.sdk.demo.java.util.SoftKeyBoardUtil;

import butterknife.BindView;

/**
 * Created by tt on 2018/2/24.
 */

public class AccountLoginFragment extends BaseFragment<AccountLoginFragmentPresenter> {

    @BindView(R.id.fragment_login_uid_edit)
    TextInputEditText uidEdit;

    @BindView(R.id.fragment_login_pwd_edit)
    TextInputEditText pwdEdit;

    @BindView(R.id.fragment_login_btn)
    Button loginBtn;

    @BindView(R.id.fragment_login_logout_layer)
    LinearLayout logoutlayer;

    @BindView(R.id.fragment_logout_btn)
    Button logoutBtn;

    @BindView(R.id.fragment_login_layer)
    LinearLayout loginLayer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected AccountLoginFragmentPresenter initPresenter() {
        return new AccountLoginFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        logoutlayer.setVisibility(View.GONE);
        loginLayer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    /**
     * account login
     */
    private void login() {
        if (TextUtils.isEmpty(uidEdit.getText())) {
            showToastShort(getString(R.string.fragment_login_uid_hint));
            return;
        }

        if (TextUtils.isEmpty(pwdEdit.getText())) {
            showToastShort(getString(R.string.fragment_login_pwd_hint));
            return;
        }

        SoftKeyBoardUtil.hideSoftKeyboard(getContext(), loginBtn);
        getPresenter().login(uidEdit.getText().toString(), pwdEdit.getText().toString());
    }

    /**
     * account logout
     */
    private void logout() {
        SoftKeyBoardUtil.hideSoftKeyboard(getContext(), logoutBtn);
        getPresenter().logout();
    }

    public void showView(boolean isLogin) {
        loginLayer.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        logoutlayer.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }
}
