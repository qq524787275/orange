package com.zhuzichu.mvvm.databinding.textview

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-28
 * Time: 11:40
 */
@BindingAdapter("strikethrough")
fun strikethrough(textView: TextView, text: String) {
    textView.text = HtmlCompat.fromHtml("<s>$text</s>", HtmlCompat.FROM_HTML_MODE_COMPACT);
}

@BindingAdapter("fromHtml")
fun fromHtml(textView: TextView, text: String) {
    textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT);
}