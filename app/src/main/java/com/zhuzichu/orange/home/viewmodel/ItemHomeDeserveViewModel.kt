package com.zhuzichu.orange.home.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.bean.DeserveBean
import com.zhuzichu.orange.shopdetail.fragment.ShopDetailFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-04
 * Time: 17:06
 */
class ItemHomeDeserveViewModel(
    viewModel: HomeViewModel,
    var deserveBean: DeserveBean
) : ItemViewModel<HomeViewModel>(viewModel) {
    val color = ColorGlobal
    val onClickItem = BindingCommand<Any>({
        viewModel.startFragment(
            ShopDetailFragment(), bundleOf(
                ShopDetailFragment.ITEMID to deserveBean.itemid,
                ShopDetailFragment.TYPE to deserveBean.shoptype,
                ShopDetailFragment.ITEMENDPRICE to deserveBean.itemendprice,
                ShopDetailFragment.ITEMPRICE to deserveBean.itemprice
            )
        )
    })
}