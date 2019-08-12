package com.zhuzichu.orange.shopdetail.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.ui.picture.PictureActivity

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
    var imageUrl: String,
    var position: Int
) :
    ItemViewModel<ShopDetailOneViewModel>(viewModel) {

    val onItemClick = BindingCommand<Any>({
        viewModel.startActivity(
            clz = PictureActivity::class.java, bundle = bundleOf(
                PictureActivity.URLS to images,
                PictureActivity.POSITION to position
            )
        )
    })
}