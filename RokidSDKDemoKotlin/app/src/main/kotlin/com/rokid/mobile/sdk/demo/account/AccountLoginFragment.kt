package com.rokid.mobile.sdk.demo.account

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.device.RKDevice
import com.rokid.mobile.lib.xbase.account.callback.ILoginResultCallback
import com.rokid.mobile.lib.xbase.account.callback.ILogoutResultCallback
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.account_fragment_login.view.*

/**
 * Created by wangshuwen on 2017/12/4.
 */
class AccountLoginFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.account_fragment_login
    }

    override fun initViews() {
        showView(!TextUtils.isEmpty(RokidMobileSDK.account.token))
    }

    override fun initListeners() {
        rootView!!.account_logout_btn.setOnClickListener {
            logout()
        }

        rootView!!.account_login_btn.setOnClickListener {
            login()
        }
    }

    /**
     * 登录
     */
    private fun login() {
        if (TextUtils.isEmpty(rootView!!.account_login_username_et.text)) {
            Toast.makeText(this@AccountLoginFragment.activity, "请输入用户名", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(rootView!!.account_login_pwd_et.text)) {
            Toast.makeText(this@AccountLoginFragment.activity, "请输入密码", Toast.LENGTH_SHORT).show()
            return
        }

        RokidMobileSDK.account.login(rootView!!.account_login_username_et.text.toString(),
                rootView!!.account_login_pwd_et.text.toString(), object : ILoginResultCallback {

            override fun onLoginSucceed() {
                this@AccountLoginFragment.activity.runOnUiThread {
                    toast("登录成功")

                    showView(true)
                }
            }

            override fun onLoginFailed(p0: String?, p1: String?) {
                this@AccountLoginFragment.activity.runOnUiThread {
                    toast("登录失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""))
                }
            }
        })

    }

    /**
     * 登出
     */
    private fun logout() {
        RokidMobileSDK.account.logout(object : ILogoutResultCallback {
            override fun onLogoutFailed(p0: String?, p1: String?) {
                this@AccountLoginFragment.activity.runOnUiThread {
                    toast("登出失败 errorCode=" + (p0
                            ?: "") + "errorMsg= " + (p1 ?: ""))
                }
            }

            override fun onLogoutSucceed() {
                this@AccountLoginFragment.activity.runOnUiThread {
                    toast("登出成功")
                    showView(false)
                }
            }

        })
    }

    private fun showView(isLogin: Boolean) {
        rootView!!.account_login_username_et.visibility = if (isLogin) View.GONE else View.VISIBLE
        rootView!!.account_login_pwd_et.visibility = if (isLogin) View.GONE else View.VISIBLE
        rootView!!.account_login_btn.visibility = if (isLogin) View.GONE else View.VISIBLE

        rootView!!.account_info.visibility = if (isLogin) View.VISIBLE else View.GONE
        rootView!!.account_logout_btn.visibility = if (isLogin) View.VISIBLE else View.GONE
    }
}