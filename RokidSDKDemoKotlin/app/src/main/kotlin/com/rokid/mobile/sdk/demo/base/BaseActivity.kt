package com.rokid.mobile.sdk.demo.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.rokid.mobile.lib.base.util.Logger

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("onCreate: ${this}")

        setContentView(layoutId())

        // 获得 ContentView mRootView 设置 沉浸式系统状态栏
        val mContentView = this.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        val rootView = mContentView.getChildAt(0) as ViewGroup
        setSystemStatusBar(rootView)

        initVariable()

        initViews()

        initListeners()
    }

    open fun initVariable() {
    }

    abstract fun layoutId(): Int

    abstract fun initViews()

    abstract fun initListeners()

    override fun onRestart() {
        super.onRestart()
        Logger.d("onRestart: ${this}")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("onResume: ${this}")
    }

    override fun onPause() {
        super.onPause()
        Logger.d("onPause: ${this}")

    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("onDestroy: ${this}")
    }

    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    /**
     * 系统状态栏字体颜色设置
     */
    fun setSystemStatusBar(rootView: View) {
        // setFitsSystemWindows 设置
        rootView.fitsSystemWindows = true

        // 4.4+ 到 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }

        // 6.0+ 状态栏 浅色 显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // 兼容 小米
        val clazz = window.javaClass
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
        } catch (e: Exception) {
            // e.printStackTrace();
            Logger.w(this.javaClass.simpleName + " ,This is not Miui")
        }

        // 兼容 魅族
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = value and bit.inv()

            meizuFlags.setInt(lp, value)
            window.attributes = lp
        } catch (e: Exception) {
            // e.printStackTrace();
            Logger.w(this.javaClass.simpleName + " ,This is not MEIZU")
        }

    }

}