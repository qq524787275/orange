package com.zhuzichu.mvvm.utils

import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.style.ImageSpan
import androidx.core.content.ContextCompat
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.widget.CenterAlignImageSpan

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-31
 * Time: 15:35
 */
const val ICON_TAG = "[icon]"

fun Int.toStringById(): String {
    return AppGlobal.context.resources.getString(this)
}

fun Double.format2(): String {
    return String.format("%.2f", this)
}

fun String.spannable(): SpannableStringBuilder {
    return SpannableStringBuilder(this)
}

fun SpannableStringBuilder.append(iconId: Int, width: Int, height: Int): SpannableStringBuilder {
    val start = this.length
    this.append(ICON_TAG)
    val end = this.length
    val drawable = ContextCompat.getDrawable(AppGlobal.context, iconId)
    drawable?.let {
        it.setBounds(0, 0, dip2px(width.toFloat()), dip2px(height.toFloat()))
        val imageSpan = CenterAlignImageSpan(it, ImageSpan.ALIGN_BASELINE)
        this.setSpan(imageSpan, start, end, SPAN_INCLUSIVE_EXCLUSIVE)
    }
    return this
}