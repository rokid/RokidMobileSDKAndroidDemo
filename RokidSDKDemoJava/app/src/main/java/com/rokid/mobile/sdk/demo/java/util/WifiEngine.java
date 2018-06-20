package com.rokid.mobile.sdk.demo.java.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.rokid.mobile.lib.base.util.CollectionUtils;
import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.entity.bean.wifi.WifiBean;
import com.rokid.mobile.lib.xbase.binder.wifi.WifiStateChangeListener;
import com.rokid.mobile.sdk.demo.java.DemoApplication;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;


public class WifiEngine {

    private WifiManager wifiManager;

    private WifiStateChangeReceiver receiver;

    private WifiStateChangeListener mListener;

    private static volatile WifiEngine instance;

    private WifiEngine() {
        wifiManager = (WifiManager) DemoApplication.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        registerReceiver();
    }

    public static WifiEngine getInstance() {
        if (instance == null) {
            synchronized (WifiEngine.class) {
                if (instance == null) {
                    instance = new WifiEngine();
                }
            }
        }
        return instance;
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        Logger.d("register the WifiStateChangeReceiver.");
        receiver = new WifiStateChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        DemoApplication.getContext().getApplicationContext().registerReceiver(receiver, filter);
    }


    /**
     * 清除ssid系统自带的引号
     * 获得当前连接的热点 用上面方法 可能获得的结果为： "0x" 或 "<unknown ssid>"
     *
     * @param ssid
     * @return
     */
    private String clearQuotesFromSSID(String ssid) {
        if (TextUtils.isEmpty(ssid)) {
            Logger.e("ssid is null do nothing");
            return ssid;
        }
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if ("0x".equals(ssid) || "<unknown ssid>".equals(ssid)) {
            ssid = "";
        }
        return ssid;
    }

    /**
     * wifi开关发生变化的监听
     *
     * @param listener
     */
    public void registerStateChangeListener(WifiStateChangeListener listener) {
        Logger.i("registerStateChangeListener is called");
        this.mListener = listener;
    }

    /**
     * 注销wifi开关发生变化的监听
     */
    public void unRegisterStateChangeListener() {
        Logger.i("unRegisterStateChangeListener is called");
        this.mListener = null;
    }

    /**
     * 获取wifi的开关状态
     *
     * @return
     */
    public boolean getWifiState() {
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    /**
     * 获取正在连接的 wifi 信息
     */
    public WifiBean getConnectWifiInfo() {
        if (!getWifiState()) {
            return null;
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return null;
        }

        WifiBean wifiBean = new WifiBean();
        wifiBean.setBssid(wifiInfo.getBSSID());
        wifiBean.setSsid(clearQuotesFromSSID(wifiInfo.getSSID()));
        wifiBean.setLevel(wifiInfo.getRssi());

        Logger.i(" getConnectWifiInfo wifiBean = " + wifiBean.toString());
        return wifiBean;
    }

    /**
     * 获取目标 wifi 的 frequency
     *
     * @param wifiBean 被设置的wifi对象
     * @return 当前 wifi 的 frequency，-1 表示得不到 wifi frequency
     */
    public int getTargetWifiFrequency(WifiBean wifiBean) {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (CollectionUtils.isEmpty(scanResults)) {
            Logger.d("Mini scanResults is null or size is 0");
            return -1;
        }

        for (ScanResult scanResult : scanResults) {
            if (scanResult.SSID.equals(clearQuotesFromSSID(wifiBean.getSsid()))) {
                Logger.d("WifiEngine target wifi frequency = " + scanResult.frequency);
                return scanResult.frequency;
            }
        }
        return -1;
    }

    /**
     * 扫描wifi列表信息
     */
    public List<WifiBean> startWifiScan() {
        Logger.i("startWifiScan is called ");
        wifiManager.startScan();

        List<ScanResult> scanResults = wifiManager.getScanResults();

        Logger.i("startWifiScan  size=" + scanResults.size());

        ArrayList<WifiBean> wifiList = new ArrayList<>();

        Label:
        for (ScanResult scanResult : scanResults) {

            if (TextUtils.isEmpty(scanResult.SSID)) {
                continue;
            }

            //过滤掉5G频段的WiFi
            if (is5GHz(scanResult.frequency)) {
                continue;
            }

            for (WifiBean wifiBean : wifiList) {
                if (wifiBean.getSsid().equals(scanResult.SSID)) {
                    continue Label;
                }
            }

            WifiBean wifiBean = new WifiBean();
            wifiBean.setBssid(scanResult.BSSID);
            wifiBean.setLevel(scanResult.level);
            wifiBean.setSsid(scanResult.SSID);
            wifiBean.setFrequency(scanResult.frequency);
            wifiList.add(wifiBean);
        }

        return wifiList;

    }

    /**
     * 释放资源
     */
    public void release() {
        Logger.d("release the WifiEngine.");
        DemoApplication.getContext().getApplicationContext().unregisterReceiver(receiver);
        instance = null;
    }

    class WifiStateChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                Logger.i("wifi state change !!  wifiState=" + wifiState);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        if (mListener != null) {
                            mListener.onChange(false);
                        }
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        if (mListener != null) {
                            mListener.onChange(true);
                        }
                        break;
                    //
                }
            }
        }
    }

    /**
     * 判断当前WiFi是否是5G频段
     */
    public boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }

    /**
     * 判断用户输入的wifi ssid是否在当前扫描到的wifi列表中
     */
    public WifiBean isVisibleWifi(String wifiSsid, List<WifiBean> wifiBeans) {
        for (WifiBean wifiBean : wifiBeans) {
            if (wifiBean.getSsid().equals(wifiSsid)) {
                return wifiBean;
            }
        }
        return null;
    }
}
