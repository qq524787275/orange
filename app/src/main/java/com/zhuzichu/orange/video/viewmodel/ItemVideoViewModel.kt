package com.zhuzichu.orange.video.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.orange.bean.ShopBean

class ItemVideoViewModel(
    viewModel: VideoViewModel,
   var shopBean: ShopBean
) : ItemViewModel<VideoViewModel>(viewModel) {

}