package com.zhuzichu.orange.category.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.global.color.ColorGlobal

class ItemTitleViewModel(
    viewModel: CategoryViewModel,
    var category: CategoryBean
) :
    ItemViewModel<CategoryViewModel>(viewModel) {
    val color = ColorGlobal

}