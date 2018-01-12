package com.rokid.mobilesdkdemo.util

import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.text.TextUtils
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.wifi.WifiBean
import com.rokid.mobile.sdk.demo.MyApplication
import java.util.*

/**
 * Created by wangshuwen on 2017/12/23.
 */

object WifiEngine {

    private val wifiManager: WifiManager


    /**
     * 获取wifi的开关状态
     *
     * @return
     */
    val wifiState: Boolean
        get() = wifiManager.isWifiEnabled


    /**
     * 获取正在连接的wifi信息
     * @return
     */
    val connectWifiInfo: WifiBean?
        get() {
            if (!wifiState) {
                return null
            }
            val wifiInfo = wifiManager!!.connectionInfo ?: return null
            val wifiBean = WifiBean()
            wifiBean.bssid = wifiInfo.bssid
            wifiBean.ssid = clearQuotesFromSSID(wifiInfo.ssid)
            wifiBean.levle = wifiInfo.rssi
            Logger.i("wifiBean=" + wifiBean.toString())
            return wifiBean
        }

    init {
        wifiManager = MyApplication.getContext().applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
    }


    /**
     * 清楚ssid系统自带的引号
     * 获得当前连接的热点 用上面方法 可能获得的结果为： "0x" 或 "<unknown ssid>"
     * @param ssid
     * @return
    </unknown> */
    private fun clearQuotesFromSSID(ssid: String): String? {
        var ssid = ssid
        if (TextUtils.isEmpty(ssid)) {
            Logger.e("ssid is null do nothing")
            return ssid
        }
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length - 1)
        }
        if ("0x" == ssid || "<unknown ssid>" == ssid) {
            ssid = ""
        }
        return ssid
    }


    /**
     * 描wifi列表信息
     */
    fun startWifiScan(): List<WifiBean> {
        Logger.i("startWifiScan is called ")
        wifiManager!!.startScan()

        val scanResults = wifiManager.scanResults

        Logger.i("startWifiScan  size=" + scanResults.size)

        val wifiList = ArrayList<WifiBean>()

        Label@ for (scanResult in scanResults) {

            if (TextUtils.isEmpty(scanResult.SSID)) {
                continue
            }

            for (wifiBean in wifiList) {
                if (wifiBean.ssid == scanResult.SSID) {
                    continue@Label
                }
            }

            val wifiBean = WifiBean()
            wifiBean.bssid = scanResult.BSSID
            wifiBean.levle = scanResult.level
            wifiBean.ssid = scanResult.SSID
            wifiList.add(wifiBean)
        }

        return wifiList

    }


}
