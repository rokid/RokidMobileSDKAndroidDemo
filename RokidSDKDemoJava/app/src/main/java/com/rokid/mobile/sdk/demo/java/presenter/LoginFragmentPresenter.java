package com.rokid.mobile.sdk.demo.java.presenter;

import android.util.Log;

import com.rokid.mobile.lib.xbase.account.callback.ILoginResultCallback;
import com.rokid.mobile.lib.xbase.account.callback.ILogoutResultCallback;
import com.rokid.mobile.sdk.RokidMobileSDK;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.activity.MainActivity;
import com.rokid.mobile.sdk.demo.java.fragment.LoginFragment;

/**
 * Created by tt on 2018/2/24.
 */

public class LoginFragmentPresenter extends BaseFragmentPresenter<LoginFragment> {

    private static final String TAG = LoginFragmentPresenter.class.getSimpleName();

    public LoginFragmentPresenter(LoginFragment fragment) {
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
                getFragment().showToast(getFragment().getString(R.string.fragment_login_success));
                MainActivity.ISLOGIN = true;
                MainActivity.UID = uid;
            }

            @Override
            public void onLoginFailed(String s, String s1) {
                Log.d(TAG, "login failed, errorCode = " + s + " , errorMsg =  " + s1);
                getFragment().showToast(getFragment().getString(R.string.fragment_login_fail)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
                MainActivity.ISLOGIN = false;
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
                getFragment().showToast(getFragment().getString(R.string.fragment_logout_success));
                MainActivity.ISLOGIN = false;
                MainActivity.UID = "";
            }

            @Override
            public void onLogoutFailed(String s, String s1) {
                Log.d(TAG, "logout failed");
                getFragment().showToast(getFragment().getString(R.string.fragment_logout_success)
                        + "\n errorCode = " + s + " , errorMsg =  " + s1);
            }
        });
    }
}
