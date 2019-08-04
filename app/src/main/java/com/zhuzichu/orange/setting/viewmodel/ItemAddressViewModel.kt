package com.zhuzichu.orange.setting.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.toast

class ItemAddressViewModel(
    viewModel: AddressDialogViewModel,
    val id: String,
    val name: String,
    val position: Int,
    val type: Int,
    val isSeleted: Boolean
) : ItemViewModel<AddressDialogViewModel>(viewModel) {
    val color = ColorGlobal

    val onClickItem = BindingCommand<Any>({
        viewModel.selectAddress(this)
    })
}