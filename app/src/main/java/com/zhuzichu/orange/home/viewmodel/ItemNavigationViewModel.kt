package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel

class ItemNavigationViewModel(
    viewModel: HomeViewModel,
    var title: String,
    var iconId: Int
) : ItemViewModel<HomeViewModel>(viewModel)