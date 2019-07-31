package com.zhuzichu.mvvm.databinding.textview

import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.R

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

@BindingAdapter("textColor")
fun textColor(textView: TextView, @NonNull color: Int? = null) {
    if (color != null) {
        textView.setTextColor(color)
    }
}


@BindingAdapter("bindSex")
fun bindSex(textView: TextView, @NonNull sex: Int? = null) {
    val array = textView.context.resources.getStringArray(R.array.list_sex)
    when (sex) {
        0 -> {
            textView.text = array[0]
        }
        1 -> {
            textView.text = array[1]
        }
        2 -> {
            textView.text = array[2]
        }
        else -> {
            textView.text = array[0]
        }
    }
}