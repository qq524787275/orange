package com.zhuzichu.mvvm.utils

import com.zhuzichu.mvvm.global.AppGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-31
 * Time: 15:35
 */

fun Int.toStringById(): String {
    return AppGlobal.context.resources.getString(this)
}