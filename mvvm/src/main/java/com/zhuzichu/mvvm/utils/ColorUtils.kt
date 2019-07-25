package com.zhuzichu.mvvm.utils

import android.graphics.Color
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

internal fun Int.isColorLight(): Boolean {
    if (this == Color.BLACK) {
        return false
    } else if (this == Color.WHITE || this == Color.TRANSPARENT) {
        return true
    }
    val darkness =
        1 - (0.299 * Color.red(this) +
                0.587 * Color.green(this) +
                0.114 * Color.blue(this)) / 255
    return darkness < 0.4
}