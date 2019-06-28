package com.zhuzichu.orange.sort.viewmodel

import androidx.databinding.ObservableBoolean
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.bean.SortBean

class ItemLeftViewModel(viewModel: SortViewModel, var sortBean: SortBean) : ItemViewModel<SortViewModel>(viewModel) {
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    var clickItem = BindingCommand<Any>( {
        if (isSelected.get()) {
            return@BindingCommand
        }
        viewModel.selectLeftItem(this)
    })

}