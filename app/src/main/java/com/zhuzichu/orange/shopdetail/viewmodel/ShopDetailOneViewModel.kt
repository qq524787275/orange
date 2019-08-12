package com.zhuzichu.orange.shopdetail.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

@SuppressLint("CheckResult")
class ShopDetailOneViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val listBanner =
        DiffObservableList(itemDiffOf<ItemShopDetalBannerViewModel> { oldItem, newItem -> oldItem.imageUrl == newItem.imageUrl })

    val itemBindBanner = itemBindingOf<Any>(BR.item, R.layout.item_shopdetail_banner)

    fun loadShopDetail(itemid: String) {
        NetRepositoryImpl.getShopDetail(itemid)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val list = it.data.taobao_image.split(",").toList()
                listBanner.update(
                    list.mapIndexed { index, item ->
                        ItemShopDetalBannerViewModel(this@ShopDetailOneViewModel, list,item, index)
                    }
                )

            }, {
                handleThrowable(it)
            })
    }
}