package com.zhuzichu.mvvm.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.zhuzichu.mvvm.App
import com.zhuzichu.mvvm.R

fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) = makeText(App.context, message, duration).show()

fun Any.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
    return makeText(App.context, this.toString(), duration).apply {
        show()
    }
}

fun makeText(
    @NonNull context: Context,
    @NonNull text: CharSequence,
    duration: Int
): Toast {
    val result = Toast(context)

    val inflate = LayoutInflater.from(context)
    val tv = inflate.inflate(R.layout.layout_toast, null)as TextView
    tv.text = text

    result.view = tv
    result.duration = duration

    return result
}