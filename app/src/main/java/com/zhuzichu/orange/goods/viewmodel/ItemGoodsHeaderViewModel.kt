package com.zhuzichu.orange.goods.viewmodel

import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.mvvm.view.banner.AutoPlayRecyclerView
import com.zhuzichu.mvvm.view.banner.ScaleLayoutManager
import com.zhuzichu.mvvm.widget.DotsIndicator
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-26
 * Time: 15:23
 */
class ItemGoodsHeaderViewModel(viewModel: GoodsViewModel) : ItemViewModel<GoodsViewModel>(viewModel) {
    val color = ColorGlobal
    val title = ObservableField<CharSequence>()
    val bannerList =
        AsyncDiffObservableList(itemDiffOf<ItemGoodsBannerViewModel> { oldItem, newItem -> oldItem.url == newItem.url })
    val bannerItemBind = itemBindingOf<Any>(BR.item, R.layout.item_goods_banner)

    lateinit var dots: DotsIndicator
    lateinit var banner: AutoPlayRecyclerView

    val initDots = BindingCommand<DotsIndicator>(consumer = {
        dots = it
    })

    val initBanner = BindingCommand<AutoPlayRecyclerView>(consumer = {
        banner = it
        val scaleLayoutManager = ScaleLayoutManager(viewModel._activity, dip2px(5f))
        scaleLayoutManager.minScale = 0.9f
        it.layoutManager = scaleLayoutManager
    })

    fun updateBanner(list: List<String>) {
        bannerList.update(list.map {
            ItemGoodsBannerViewModel(viewModel, it.plus("_500x500.jpg"))
        })
        dots.postDelayed({
            dots.attachRecyclerView(banner)
        }, 150)
    }
}