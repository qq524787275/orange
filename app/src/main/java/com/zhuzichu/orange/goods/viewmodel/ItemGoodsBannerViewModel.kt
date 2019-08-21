package com.zhuzichu.orange.goods.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-21
 * Time: 11:32
 */
class ItemGoodsBannerViewModel(
    viewModel: GoodsViewModel,
    val url: String
) : ItemViewModel<GoodsViewModel>(viewModel) {
}