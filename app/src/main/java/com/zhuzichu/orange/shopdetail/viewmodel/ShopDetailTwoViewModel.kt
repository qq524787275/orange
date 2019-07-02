package com.zhuzichu.orange.shopdetail.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.NetRepositoryImpl
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

@SuppressLint("CheckResult")
class ShopDetailTwoViewModel(application: Application) : BaseViewModel(application) {
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemDescViewModel && newItem is ItemDescViewModel) {
                oldItem.url == newItem.url
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }
    val list = AsyncDiffObservableList<Any>(diff)
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_shop_detail_desc)


    fun loadShopDetailDesc(itemid: String, type: String) {
        NetRepositoryImpl.getShopDetailDesc(itemid, type)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .subscribe({
                list.update(it.map { item ->
                    ItemDescViewModel(this@ShopDetailTwoViewModel, item)
                })
                showContent()
            }, {
                handleThrowable(it)
                showError()
            })
    }
}