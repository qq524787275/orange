package com.zhuzichu.orange.home.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.bean.SalesBean
import com.zhuzichu.orange.shopdetail.fragment.ShopDetailFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 11:14
 */
class ItemHomeBannerViewModel(
    viewModel: HomeViewModel,
    var salesBean: SalesBean
) : ItemViewModel<HomeViewModel>(viewModel) {
    val onItemClick = BindingCommand<Any>({
        viewModel.startFragment(
            ShopDetailFragment(), bundleOf(
                ShopDetailFragment.ITEMID to salesBean.itemid,
                ShopDetailFragment.TYPE to salesBean.shoptype,
                ShopDetailFragment.ITEMENDPRICE to salesBean.itemendprice,
                ShopDetailFragment.ITEMPRICE to salesBean.itemprice
            )
        )
    })
}