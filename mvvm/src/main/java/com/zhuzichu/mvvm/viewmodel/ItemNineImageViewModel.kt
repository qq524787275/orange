package com.zhuzichu.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.zhuzichu.mvvm.activity.ImagePreviewActivity
import com.zhuzichu.mvvm.databinding.command.BindingCommand

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-26
 * Time: 18:31
 */
class ItemNineImageViewModel(
    var context: Context,
    var list: ArrayList<String>,
    var url: String
) : ViewModel() {
    val onClickItem = BindingCommand<Any>( {
        ImagePreviewActivity.start(context, list, url)
    })
}