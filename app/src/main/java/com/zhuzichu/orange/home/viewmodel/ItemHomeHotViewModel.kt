package com.zhuzichu.orange.home.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.goods.fragment.GoodsFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-09
 * Time: 13:40
 */
class ItemHomeHotViewModel(
    viewModel: HomeViewModel,
    var goodsBean: GoodsBean
) : ItemViewModel<HomeViewModel>(viewModel) {

    val onClickItem = BindingCommand<Any>({
        viewModel.startFragment(
            GoodsFragment(), bundleOf(
                GoodsFragment.GOODS_INFO to goodsBean
            )
        )
    })
}