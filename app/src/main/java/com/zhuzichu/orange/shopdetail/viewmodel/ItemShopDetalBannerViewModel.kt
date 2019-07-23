package com.zhuzichu.orange.shopdetail.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.ui.previewimage.PreviewImageActivity

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-23
 * Time: 13:44
 */
class ItemShopDetalBannerViewModel(
    viewModel: ShopDetailOneViewModel,
    var images: List<String>,
    var imageUrl: String
) :
    ItemViewModel<ShopDetailOneViewModel>(viewModel) {

    val onItemClick = BindingCommand<Any>({
        PreviewImageActivity.start(viewModel._context, images as ArrayList<String>, imageUrl)
    })
}