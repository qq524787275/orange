package com.zhuzichu.orange.mine.viewmodel

import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.orange.flutter.MainFlutterFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 09:51
 */
class ItemSmallProgramViewModel(
    viewModel: SmallProgramViewModel,
    var id: Int,
    var title: String
) : ItemViewModel<BaseViewModel>(viewModel) {
    val color = ColorGlobal
    val onItemClick = BindingCommand<Any>({
        when (id) {
            0 -> {
                viewModel.startFragment(MainFlutterFragment())
            }
            else -> {
            }
        }
    })
}