package com.zhuzichu.orange.ui.picture.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel

class ItemPictureViewModel(
    viewModel: PictureViewModel,
    val path: String
) : ItemViewModel<PictureViewModel>(viewModel) {

}