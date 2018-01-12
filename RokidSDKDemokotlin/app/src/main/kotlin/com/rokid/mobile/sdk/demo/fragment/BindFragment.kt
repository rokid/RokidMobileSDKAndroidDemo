package com.rokid.mobile.sdk.demo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.rokid.lib_appbase.recyclerview.adapter.BaseRVAdapter
import com.rokid.mobile.lib.base.util.Logger
import com.rokid.mobile.lib.entity.bean.bluetooth.BTDeviceBean
import com.rokid.mobile.lib.entity.bean.bluetooth.DeviceBinderData
import com.rokid.mobile.lib.entity.bean.wifi.WifiBean
import com.rokid.mobile.lib.xbase.binder.bluetooth.callBack.IBTConnectCallBack
import com.rokid.mobile.lib.xbase.binder.bluetooth.callBack.IBTSendCallBack
import com.rokid.mobile.lib.xbase.binder.bluetooth.exception.BleException
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.adapter.item.BleItem
import com.rokid.mobilesdkdemo.util.WifiEngine
import kotlinx.android.synthetic.main.fragment_bind.view.*

/**
 *
 *配网模块
 * Created by wangshuwen on 2017/12/4.
 */
class BindFragment : Fragment() {

    private var rootView: View? = null
    /**
     * 显示蓝牙设备的RecyclerView
     */
    private lateinit var rv: RecyclerView
    /**
     * 扫描蓝牙的btn
     */
    private lateinit var scanBt: Button
    /**
     * 蓝牙前缀的输入框
     */
    private lateinit var bleNameEt: EditText
    /**
     * 绑定的btn
     */
    private lateinit var sendDataBtn: Button
    /**
     * wifi密码的输入框
     */
    private lateinit var wifiPasswordEdit: EditText
    /**
     * wifi名字的输入框
     */
    private lateinit var wifiSSIDEdit: EditText
    /**
     * wifi信息
     */
    private var wifiInfo: WifiBean? = null
    /**
     * 当前连接的蓝牙item
     */
    private var currentBleItem: BleItem? = null

    private lateinit var mAdapter: BaseRVAdapter<BleItem>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            rootView = inflater!!.inflate(R.layout.fragment_bind, container!!, false)
            initView()
            initListener()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        initWifiInfo()
    }

    private fun initView() {
        scanBt = rootView!!.fragment_bind_scan

        bleNameEt = rootView!!.fragment_bind_blename_et
        bleNameEt.setSelection(bleNameEt.length())

        wifiPasswordEdit = rootView!!.fragment_bind_wifipwd_txt
        wifiSSIDEdit = rootView!!.fragment_bind_wifiname_txt
        sendDataBtn = rootView!!.fragment_bind_send_data_btn

        rv = rootView!!.fragment_bind_rv
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            mAdapter = BaseRVAdapter()
            adapter = mAdapter

        }

    }

    private fun initListener() {
        //扫描按钮
        scanBt.setOnClickListener {
            val nameMark = bleNameEt.text.toString()
            if (nameMark.isEmpty()) {
                Logger.e("BLE name mark is empty please edit")
                Toast.makeText(this@BindFragment.activity, "请输入蓝牙前缀 ~~~", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //停止扫描
            RokidMobileSDK.binder.stopBTScanAndClearList()
            mAdapter.clearAllItemView()
            doStartScanBle(nameMark)
        }
        //蓝牙设备点击
        mAdapter.setOnItemViewClickListener { bleItem, sectionKey, sectionItemPosition ->
            Logger.i("onItemClick position=" + sectionItemPosition + " blueToothName=" + bleItem.data.name)
            stopScan()
            currentBleItem = bleItem
            bleItem.showProgress()
            connectBle(bleItem.data.name)
        }
        //发送绑定数据 btn
        sendDataBtn.setOnClickListener {
            sendBindData()
        }

    }


    /**
     * 初始化wifi信息
     */
    private fun initWifiInfo() {
        Logger.i("initWifiInfo is called")
        wifiInfo = WifiEngine.connectWifiInfo

        wifiInfo?.let {
            val ssid = wifiInfo!!.getSsid()
            wifiSSIDEdit.setText(ssid)
            wifiSSIDEdit.setSelection(wifiSSIDEdit.length())
        }

    }

    /**
     * 扫描蓝牙
     */
    fun doStartScanBle(nameMark: String) {
        RokidMobileSDK.binder.startBTScan(nameMark) { bleDevice ->
            bleDevice?.let {
                mAdapter.addItemView(BleItem(bleDevice))
            }
        }
    }

    /**
     * 停止扫描
     */
    fun stopScan() {
        RokidMobileSDK.binder.stopBTScan()
    }


    /**
     * 连接蓝牙
     */
    fun connectBle(bleName: String) {
        RokidMobileSDK.binder.connectBT(bleName, object : IBTConnectCallBack {

            override fun onConnectSucceed(p0: BTDeviceBean?) {
                this@BindFragment.activity.runOnUiThread {
                    Toast.makeText(this@BindFragment.activity, "连接成功 请输入wifi信息", Toast.LENGTH_LONG).show()
                    currentBleItem?.hideProgress()
                }
            }

            override fun onConnectFailed(p0: BTDeviceBean?, p1: BleException?) {
                this@BindFragment.activity.runOnUiThread {
                    Toast.makeText(this@BindFragment.activity,
                            "连接失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""),
                            Toast.LENGTH_LONG).show()
                    currentBleItem?.hideProgress()
                }
            }

        })
    }


    fun sendBindData() {
        //todo 输入框
        val userId = ""
        val bssid = wifiInfo?.bssid
        val ssid = wifiSSIDEdit.text.toString()
        val pwd = wifiPasswordEdit.text.toString()

        if (TextUtils.isEmpty(ssid) && TextUtils.isEmpty(pwd)) {
            Toast.makeText(this@BindFragment.activity, "wifi名字和密码为空 请重新输入", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(userId)) {
            Logger.e("send bind data userId is empty")
        }
        val bindData = DeviceBinderData.newBuilder()
                .userId(userId)
                .wifiPwd(pwd)
                .wifiSsid(ssid)
                .wifiBssid(bssid)
                .build()


        RokidMobileSDK.binder.sendBTBinderData(bindData, object : IBTSendCallBack {
            override fun onSendSuccess(p0: BTDeviceBean?) {
                Logger.e("Send Success -----------------")
                this@BindFragment.activity.runOnUiThread {
                    Toast.makeText(this@BindFragment.activity, "发送数据成功", Toast.LENGTH_LONG).show()

                }
            }

            override fun onSendFailed(p0: BTDeviceBean?, p1: BleException?) {
                Logger.e("SendFailed -----------------")
                this@BindFragment.activity.runOnUiThread {
                    Toast.makeText(this@BindFragment.activity,
                            "发送数据失败 errorCode=" + (p0 ?: "") + "errorMsg= " + (p1 ?: ""),
                            Toast.LENGTH_LONG).show()
                }
            }
        })
    }


}