package com.zhuzichu.orange.ui.camerax.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import java.io.File

class ItemAlbumViewModel(
    viewModel: AlbumViewModel,
    val resource: File
) : ItemViewModel<AlbumViewModel>(viewModel)