package com.zhuzichu.mvvm.utils

import android.widget.Toast
import com.zhuzichu.mvvm.App

fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(App.context, message, duration).show()

fun Any.toast( duration: Int = Toast.LENGTH_SHORT):Toast{
    return Toast.makeText(App.context,this.toString(),duration).apply {
        show()
    }
}