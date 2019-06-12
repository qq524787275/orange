package com.zhuzichu.orange.sort

import androidx.databinding.ObservableBoolean
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.bean.SortBean

class SortItemViewModel(viewModel: SortViewModel, var sortBean: SortBean) : ItemViewModel<SortViewModel>(viewModel) {
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    var clickItem = BindingCommand<Any>(BindingAction {
        if (isSelected.get()) {
            return@BindingAction
        }
        viewModel.selectItem(this)
    })

}