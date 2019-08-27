package com.zhuzichu.orange.goods.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.orange.goods.fragment.GoodsFragment
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-26
 * Time: 15:24
 */
class ItemGoodsViewModel(
    viewModel: GoodsViewModel,
    val goodsBean: GoodsBean
) : ItemViewModel<GoodsViewModel>(viewModel) {
    val color = ColorGlobal
    val onClickItem = BindingCommand<Any>({
        viewModel.startFragment(
            GoodsFragment(),
            bundleOf(
                GoodsFragment.GOODS_INFO to goodsBean
            ),
            launchMode = ISupportFragment.SINGLETOP
        )
    })
}