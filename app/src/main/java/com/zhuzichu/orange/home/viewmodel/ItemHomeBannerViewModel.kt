package com.zhuzichu.orange.home.viewmodel

import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.goods.fragment.GoodsFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 11:14
 */
class ItemHomeBannerViewModel(
    viewModel: HomeViewModel,
    var goodsBean: GoodsBean
) : ItemViewModel<HomeViewModel>(viewModel) {

    val itempic = ObservableField(goodsBean.itempic)
    val itemshorttitle = ObservableField(goodsBean.itemshorttitle)

    val onItemClick = BindingCommand<Any>({
        viewModel.startFragment(
            GoodsFragment(), bundleOf(
                GoodsFragment.GOODS_INFO to goodsBean
            )
        )
    })
}