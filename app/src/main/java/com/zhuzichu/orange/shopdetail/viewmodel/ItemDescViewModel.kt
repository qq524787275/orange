package com.zhuzichu.orange.shopdetail.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel

class ItemDescViewModel(
    viewModel: ShopDetailTwoViewModel,
    var url: String
) : ItemViewModel<ShopDetailTwoViewModel>(viewModel)