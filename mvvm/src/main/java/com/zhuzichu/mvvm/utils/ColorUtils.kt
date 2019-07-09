package com.zhuzichu.mvvm.utils

import androidx.core.content.ContextCompat
import com.zhuzichu.mvvm.global.AppGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 12:42
 */

fun getColorById(id: Int): Int {
    return ContextCompat.getColor(AppGlobal.context, id)
}

fun Int.toColorById(): Int {
    return ContextCompat.getColor(AppGlobal.context, this)
}