package com.rokid.mobile.sdk.demo.java.account;

import android.util.Log;

import com.rokid.mobile.lib.xbase.account.callback.ILoginResultCallback;
import com.rokid.mobile.lib.xbase.account.callback.ILogoutResultCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.DemoMainActivity;
import com.rokid.mobile.sdk.demo.java.base.BaseFragmentPresenter;

/**
 * Created by tt on 2018/2/24.
 */

public class AccountLoginFragmentPresenter extends BaseFragmentPresenter<AccountLoginFragment> {

    private static final String TAG = AccountLoginFragmentPresenter.class.getSimpleName();

    public AccountLoginFragmentPresenter(AccountLoginFragment fragment) {
        super(fragment);
    }

    /**
     * call rokid sdk login method
     *
     * @param uid user id
     * @param pwd user password
     */
    public void login(final String uid, String pwd) {
        RokidMobileSDK.account.login(uid, pwd, new ILoginResultCallback() {
            @Override
            public void onLoginSucceed() {
                Log.d(TAG, "login success");
                getFragment().showView(true);
                getFragment().showToastShort(getFragment().getString(R.string.fragment_login_success));
                DemoMainActivity.ISLOGIN = true;
                DemoMainActivity.UID = uid;
            }

            @Override
            public void onLoginFailed(String s, String s1) {
                Log.d(TAG, "login failed, errorCode = " + s + " , errorMsg =  " + s1);
                getFragment().showToastShort(getFragment().getString(R.string.fragment_login_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
                DemoMainActivity.ISLOGIN = false;
            }
        });
    }

    /**
     * call rokid sdk logout method
     */
    public void logout() {
        RokidMobileSDK.account.logout(new ILogoutResultCallback() {
            @Override
            public void onLogoutSucceed() {
                Log.d(TAG, "logout success");
                getFragment().showView(false);
                getFragment().showToastShort(getFragment().getString(R.string.fragment_logout_success));
                DemoMainActivity.ISLOGIN = false;
                DemoMainActivity.UID = "";
            }

            @Override
            public void onLogoutFailed(String s, String s1) {
                Log.d(TAG, "logout failed");
                getFragment().showToastShort(getFragment().getString(R.string.fragment_logout_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
            }
        });
    }
}
