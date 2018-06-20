package com.rokid.mobile.sdk.demo.other

import android.widget.Button
import com.rokid.mobile.lib.entity.bean.home.AsrErrorCorrectBean
import com.rokid.mobile.lib.xbase.home.callback.IGetAsrErrorCallback
import com.rokid.mobile.sdk.RokidMobileSDK
import com.rokid.mobile.sdk.demo.R
import com.rokid.mobile.sdk.demo.base.BaseFragment
import kotlinx.android.synthetic.main.device_fragment_list.view.*

/**
 * @author hongquan.zhao
 * Create Date:2018/3/21
 * Version:V1.0
 */
class AsrFragment : BaseFragment() {
    lateinit var carListBtn: Button
//    lateinit var mRecycler: RecyclerView;
//    lateinit var mAdapter: BaseRVAdapter<CardItem>;


    override fun layoutId(): Int = R.layout.device_fragment_list;


    override fun initViews() {
        carListBtn = rootView!!.fragment_device_list_btn
        carListBtn.setText(getString(R.string.fragment_asr_add_action))
//        mRecycler = rootView!!.fragment_device_list_rv

//        mRecycler.apply {
//            layoutManager = LinearLayoutManager(activity as Context?)
//            mAdapter = BaseRVAdapter()
//            adapter = mAdapter
//        }
    }

    override fun initListeners() {
        carListBtn.setOnClickListener {
            //            mRecycler.visibility = View.GONE
//            mAdapter.clearAllItemView()
            asrCorrect("东冯破", "东风破")
        }

    }

    fun asrCorrect(originText: String, newText: String) {
        RokidMobileSDK.vui.asrCorrectCreate(originText, newText, object : IGetAsrErrorCallback {
            override fun onGetAsrErrorFailed(p0: String?, p1: String?) {
                toast(getString(R.string.fragment_asr_add_fail)
                        + "\n errorCode = " + p0 + " , errorMsg =  " + p1)
            }

            override fun onGetAsrErrorSucceed(p0: AsrErrorCorrectBean?) {
                toast(getString(R.string.fragment_asr_add_success)
                        + "\n  = " + p0)
            }

        })
    }
}
