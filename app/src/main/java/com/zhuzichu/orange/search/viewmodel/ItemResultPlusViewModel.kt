package com.zhuzichu.orange.search.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.utils.toast

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 16:57
 */
class ItemResultPlusViewModel(
    viewModel: SearchResultPlusViewModel,
    goodsBean: GoodsBean.TbkDgMaterialOptionalResponse.ResultList.MapData,
    val spanSize: ObservableInt
) :
    ItemViewModel<SearchResultPlusViewModel>(viewModel) {
    val itemid = goodsBean.item_id
    val itempic = ObservableField(goodsBean.pict_url.plus("_310x310.jpg"))
    val itemshorttitle = ObservableField(goodsBean.short_title)
    val itemendprice = ObservableField(goodsBean.zk_final_price)
    val itemprice = ObservableField(goodsBean.reserve_price)
    val itemsale = ObservableField(goodsBean.volume.toString())
    val couponmoney = ObservableField(goodsBean.coupon_amount)

    val onClickItem = BindingCommand<Any>({
        itemid.toast()
        itemid.logi("ItemResultPlusViewModel")
    })
}