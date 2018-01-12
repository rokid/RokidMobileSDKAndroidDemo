package com.rokid.lib_appbase.exception;

import android.text.TextUtils;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/7/8
 */
public class RvAdapterException extends RuntimeException {

    public RvAdapterException(String errMsg) {
        super(!TextUtils.isEmpty(errMsg) ? errMsg : "RvAdapterException");
    }

}
