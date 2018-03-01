package com.rokid.mobile.sdk.demo.java.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.rokid.mobile.lib.base.util.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by tt on 2018/2/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public ViewGroup mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(this.getClass().getSimpleName());
        super.onCreate(savedInstanceState);

        // 分解 onCreate 使其更符合 单一职能原则
        setContentView(getLayoutId());

        // 获得 ContentView mRootView
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        mRootView = (ViewGroup) mContentView.getChildAt(0);

        // 设置 沉浸式系统状态栏
        setSystemStatusBar(mRootView);

        // 初始化变量
        initVariables(savedInstanceState);
        // 初始化监听器
        initListeners();

    }

    protected abstract int getLayoutId();

    protected abstract void initVariables(@Nullable Bundle savedInstanceState);

    protected abstract void initListeners();

    /**
     * 系统状态栏字体颜色设置
     */
    protected void setSystemStatusBar(@NonNull View rootView) {

        // setFitsSystemWindows 设置
        ViewCompat.setFitsSystemWindows(rootView, true);

        // 4.4+ 到 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        // 6.0+ 状态栏 浅色 显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 兼容 小米
        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(getWindow(), darkModeFlag, darkModeFlag);
        } catch (Exception e) {
            // e.printStackTrace();
            Logger.w(this.getClass().getSimpleName() + " ,This is not Miui");
        }

        // 兼容 魅族
        try {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            value &= ~bit;

            meizuFlags.setInt(lp, value);
            getWindow().setAttributes(lp);
        } catch (Exception e) {
            // e.printStackTrace();
            Logger.w(this.getClass().getSimpleName() + " ,This is not MEIZU");
        }
    }

}
