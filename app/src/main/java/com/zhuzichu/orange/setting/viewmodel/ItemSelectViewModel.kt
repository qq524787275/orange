package com.zhuzichu.orange.setting.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-06
 * Time: 14:15
 */
class ItemSelectViewModel(
    viewModel: SelectItemViewModel,
    val key: String,
    val value: Any,
    val isSelected: Boolean
) : ItemViewModel<SelectItemViewModel>(viewModel) {
    val color = ColorGlobal
    val onClickItem = BindingCommand<Any>({
        val data = mutableListOf<ItemSelectViewModel>()
        viewModel.list.value?.forEach {
            if (it is ItemSelectViewModel)
                data.add(ItemSelectViewModel(viewModel, it.key, it.value, it.key == key))
        }
        viewModel.list.value = data
        viewModel.onSelectItemEvent.value = this
    })
}