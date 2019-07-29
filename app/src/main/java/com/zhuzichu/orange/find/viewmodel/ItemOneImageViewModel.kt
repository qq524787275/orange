package com.zhuzichu.orange.find.viewmodel

import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.ui.previewimage.PreviewImageActivity

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-29
 * Time: 16:26
 */
class ItemOneImageViewModel(
    viewModel: BaseViewModel,
    var url: String,
    var current: String,
    var list: List<String>
) : ItemViewModel<BaseViewModel>(viewModel) {

    val onClickItem = BindingCommand<Any>({
        PreviewImageActivity.start(viewModel._context, list as ArrayList<String>, current)
    })
}