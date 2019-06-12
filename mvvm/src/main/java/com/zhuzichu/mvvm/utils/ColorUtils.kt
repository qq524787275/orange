package com.zhuzichu.mvvm.utils

import androidx.core.content.ContextCompat
import com.zhuzichu.mvvm.App

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 12:42
 */

fun getColor(id: Int): Int {
    return ContextCompat.getColor(App.context, id)
}

fun Int.toColor(): Int {
    return ContextCompat.getColor(App.context, this)
}