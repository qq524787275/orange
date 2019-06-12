package com.zhuzichu.mvvm.utils

import android.util.Log

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-29
 * Time: 17:08
 */
private const val TAG = "zzc"

fun Any.logi(tag: String = TAG) {
    Log.i(tag, this.toString())
}