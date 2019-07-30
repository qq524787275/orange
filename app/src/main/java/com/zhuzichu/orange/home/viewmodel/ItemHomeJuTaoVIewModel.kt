package com.zhuzichu.orange.home.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.bean.ShopBean
import com.zhuzichu.orange.shopdetail.fragment.ShopDetailFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-09
 * Time: 15:53
 */
class ItemHomeJuTaoVIewModel(
    viewModel: HomeViewModel,
    var shopBean: ShopBean
) : ItemViewModel<HomeViewModel>(viewModel) {
    val color = ColorGlobal
    val onClickItem = BindingCommand<Any>({
        viewModel.startFragment(
            ShopDetailFragment(), bundleOf(
                ShopDetailFragment.ITEMID to shopBean.itemid,
                ShopDetailFragment.TYPE to shopBean.shoptype,
                ShopDetailFragment.ITEMENDPRICE to shopBean.itemendprice,
                ShopDetailFragment.ITEMPRICE to shopBean.itemprice
            )
        )
    })
}