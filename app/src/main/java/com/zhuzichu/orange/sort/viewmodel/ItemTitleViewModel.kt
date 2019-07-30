package com.zhuzichu.orange.sort.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.bean.SortBean

class ItemTitleViewModel(viewModel: SortViewModel, var data: SortBean.Data) :
    ItemViewModel<SortViewModel>(viewModel) {
    val color = ColorGlobal

}