package com.zhuzichu.orange.goods.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.*
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
    val itemprice = ObservableField<String>()
    val itemendprice = ObservableField<String>()
    val bannerList =
        AsyncDiffObservableList(itemDiffOf<ItemGoodsBannerViewModel> { oldItem, newItem -> oldItem.url == newItem.url })
    val bannerItemBind = itemBindingOf<Any>(BR.item, R.layout.item_goods_banner)
    val title = ObservableField<CharSequence>()
    val showBanner = ObservableBoolean(false)
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
            showTradeUrl(_activity, url)
        }
    })

    val onClickBack = BindingCommand<Any>({
        back()
    })

    @SuppressLint("CheckResult")
    fun loadShopDetail(itemid: String, success: (() -> Unit)? = null) {
        NetRepositoryImpl.getShopDetail(itemid)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val list = it.data.taobao_image.split(",").toList()
                bannerList.update(
                    list.map { item ->
                        ItemGoodsBannerViewModel(this@GoodsViewModel, item.plus("_500x500.jpg"))
                    }
                )
                showBanner.set(true)
                success?.invoke()
            }, {
                handleThrowable(it)
            })
    }
}
