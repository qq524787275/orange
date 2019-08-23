package com.zhuzichu.orange.search.viewmodel

import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.goods.fragment.GoodsFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 16:57
 */
class ItemResultViewModel(
    viewModel: SearchResultViewModel,
    goodsBean: GoodsBean,
    val spanSize: ObservableInt
) :
    ItemViewModel<SearchResultViewModel>(viewModel) {
    val itemid = goodsBean.itemid
    val itempic = ObservableField(goodsBean.itempic.plus("_310x310.jpg"))
    val itemshorttitle = ObservableField(goodsBean.itemshorttitle)
    val itemendprice = ObservableField(goodsBean.itemendprice)
    val itemprice = ObservableField(goodsBean.itemprice)
    val itemsale = ObservableField(goodsBean.itemsale)
    val couponmoney = ObservableField(goodsBean.couponmoney)

    val onClickItem = BindingCommand<Any>({
        viewModel.startFragment(
            GoodsFragment(), bundle = bundleOf(
                GoodsFragment.GOODS_INFO to goodsBean
            )
        )
    })
}