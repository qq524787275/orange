package com.zhuzichu.orange.sort.viewmodel

import androidx.databinding.ObservableBoolean
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.bean.SortBean

class ItemLeftViewModel(viewModel: SortViewModel, var sortBean: SortBean) : ItemViewModel<SortViewModel>(viewModel) {
    val isSelected: ObservableBoolean = ObservableBoolean(false)
    val color= ColorGlobal
    val clickItem = BindingCommand<Any>( {
        if (isSelected.get()) {
            return@BindingCommand
        }
        viewModel.selectLeftItem(this)
    })

}