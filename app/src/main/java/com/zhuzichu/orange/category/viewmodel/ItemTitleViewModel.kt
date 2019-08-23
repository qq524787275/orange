package com.zhuzichu.orange.category.viewmodel

import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.global.color.ColorGlobal

class ItemTitleViewModel(
    viewModel: CategoryViewModel,
    var category: CategoryBean.Data
) :
    ItemViewModel<CategoryViewModel>(viewModel) {
    val color = ColorGlobal
    val name = ObservableField(category.next_name)
}