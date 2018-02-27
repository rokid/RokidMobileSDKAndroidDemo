package com.rokid.mobile.sdk.demo.java.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.presenter.LoginFragmentPresenter;
import com.rokid.mobile.sdk.demo.java.util.SoftKeyBoardUtil;

/**
 * Created by tt on 2018/2/24.
 */

public class LoginFragment extends BaseFragment<LoginFragmentPresenter> {

    private TextInputEditText uidEdit;

    private TextInputEditText pwdEdit;

    private Button loginBtn;

    private Button logoutBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected LoginFragmentPresenter initPresenter() {
        return new LoginFragmentPresenter(this);
    }

    @Override
    protected void initVariables(View rootView, ViewGroup container, @Nullable Bundle savedInstanceState) {
        uidEdit = rootView.findViewById(R.id.fragment_login_uid_edit);
        pwdEdit = rootView.findViewById(R.id.fragment_login_pwd_edit);
        loginBtn = rootView.findViewById(R.id.fragment_login_btn);
        logoutBtn = rootView.findViewById(R.id.fragment_logout_btn);
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
            showToast(getString(R.string.fragment_login_uid_hint));
            return;
        }

        if (TextUtils.isEmpty(pwdEdit.getText())) {
            showToast(getString(R.string.fragment_login_pwd_hint));
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

    public void showToast(String text) {
        showToastShort(text);
    }
}
