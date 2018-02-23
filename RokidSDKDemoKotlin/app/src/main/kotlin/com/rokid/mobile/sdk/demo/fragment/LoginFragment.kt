package com.rokid.mobile.sdk.demo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.device.RKDevice
import com.rokid.mobile.lib.xbase.account.callback.ILoginResultCallback
import com.rokid.mobile.lib.xbase.account.callback.ILogoutResultCallback
import com.rokid.mobile.lib.xbase.device.callback.IGetDeviceListCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * Created by wangshuwen on 2017/12/4.
 */
class LoginFragment : Fragment() {

    private var rootView: View? = null

    private lateinit var userNmaeEt: EditText

    private lateinit var pwdEt: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            rootView = inflater!!.inflate(R.layout.fragment_login, container!!, false)
            initView()
            initListener()
        }
        return rootView
    }

    private fun initListener() {
        rootView!!.fragment_bind_logout_btn.setOnClickListener {
            logout()
        }

        rootView!!.fragment_bind_login_btn.setOnClickListener {
            login()
        }
    }


    private fun initView() {
        userNmaeEt = rootView!!.fragment_login_username_et
        pwdEt = rootView!!.fragment_login_pwd_et
    }


    /**
     * 登录
     */
    fun login() {
        if (TextUtils.isEmpty(userNmaeEt.text)) {
            Toast.makeText(this@LoginFragment.activity, "请输入用户名", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(pwdEt.text)) {
            Toast.makeText(this@LoginFragment.activity, "请输入密码", Toast.LENGTH_SHORT).show()
            return
        }

        RokidMobileSDK.account.login(userNmaeEt.text.toString(), pwdEt.text.toString(), object : ILoginResultCallback {

            override fun onLoginSucceed() {
                this@LoginFragment.activity.runOnUiThread {
                    Toast.makeText(this@LoginFragment.activity, "登录成功", Toast.LENGTH_SHORT).show()
                }

                getDeviceList()


            }

            override fun onLoginFailed(p0: String?, p1: String?) {
                this@LoginFragment.activity.runOnUiThread {
                    Toast.makeText(this@LoginFragment.activity, "登录失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""), Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

    /**
     * 获取设备按钮
     */
    fun getDeviceList() {
        RokidMobileSDK.device.getDeviceList(object : IGetDeviceListCallback {
            override fun onGetDeviceListSucceed(p0: MutableList<RKDevice>?) {
                    Logger.i("getDevceisuccess")
            }

            override fun onGetDeviceListFailed(p0: String?, p1: String?) {
            }

        })


    }

    /**
     * 登出
     */
    fun logout() {
        RokidMobileSDK.account.logout(object : ILogoutResultCallback {
            override fun onLogoutFailed(p0: String?, p1: String?) {
                this@LoginFragment.activity.runOnUiThread {
                    Toast.makeText(this@LoginFragment.activity, "登出失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLogoutSucceed() {
                this@LoginFragment.activity.runOnUiThread {
                    Toast.makeText(this@LoginFragment.activity, "登出成功", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}