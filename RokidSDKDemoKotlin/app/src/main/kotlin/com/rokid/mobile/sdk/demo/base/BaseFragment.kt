package com.rokid.mobile.sdk.demo.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rokid.mobile.lib.base.util.Logger

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2018/2/26
 */
abstract class BaseFragment : Fragment() {

    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.d("onCreateView: ${this}")

        if (null == rootView) {
            rootView = inflater!!.inflate(layoutId(), null)

            initViews()

            initListeners()
        }
        return rootView
    }

    abstract fun layoutId(): Int

    abstract fun initViews()

    abstract fun initListeners()

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
        activity!!.runOnUiThread {
            Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
        }
    }

}