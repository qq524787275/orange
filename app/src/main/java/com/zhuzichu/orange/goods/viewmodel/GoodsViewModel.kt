package com.zhuzichu.orange.goods.viewmodel

import android.app.Application
import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.main.fragment.MainFragment
import com.zhuzichu.orange.utils.checkAuth
import com.zhuzichu.orange.utils.showTradeDetail
import com.zhuzichu.orange.utils.showTradeUrl
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import me.yokeyword.fragmentation.ISupportFragment

class GoodsViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val itemid = ObservableField<String>()
    val itemprice = ObservableField("")
    val itemendprice = ObservableField("")
    val bannerList =
        AsyncDiffObservableList(itemDiffOf<ItemGoodsBannerViewModel> { oldItem, newItem -> oldItem.url == newItem.url })
    val bannerItemBind = itemBindingOf<Any>(BR.item, R.layout.item_goods_banner)
    val title = ObservableField<CharSequence>()
    lateinit var url: String

    val onClickHome = BindingCommand<Any>({
        startFragment(
            MainFragment(), launchMode = ISupportFragment.SINGLETASK, bundle = bundleOf(
                MainFragment.POSITION to 0
            )
        )
    })

    val onClickCollection = BindingCommand<Any>({
        "暂未开发".toast()
    })

    val onClickItemprice = BindingCommand<Any>({
        checkAuth(_activity) {
            itemid.get()?.let { showTradeDetail(_activity, it) }
        }
    })

    val onClickItemendprice = BindingCommand<Any>({
        checkAuth(_activity) {
            //            itemid.get()?.let {
//                showTradeDetail(_activity, it)
//            }
            showTradeUrl(_activity, "https:".plus(url))
        }
    })

    val onClickBack = BindingCommand<Any>({
        back()
    })
}
