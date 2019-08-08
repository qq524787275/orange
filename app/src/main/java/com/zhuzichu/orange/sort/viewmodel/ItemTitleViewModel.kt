package com.zhuzichu.orange.sort.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.bean.SortBean

class ItemTitleViewModel(
    viewModel: SortViewModel,
    var category: CategoryBean
) :
    ItemViewModel<SortViewModel>(viewModel) {
    val color = ColorGlobal

}