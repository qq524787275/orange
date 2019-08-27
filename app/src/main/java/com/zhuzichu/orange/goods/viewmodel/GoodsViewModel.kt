package com.zhuzichu.orange.goods.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
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
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import me.yokeyword.fragmentation.ISupportFragment

class GoodsViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val itemprice = ObservableField<String>()
    val itemendprice = ObservableField<String>()

    val itemBind = OnItemBindClass<Any>().apply {
        map<ItemGoodsHeaderViewModel>(BR.item, R.layout.item_goods_header)
        map<ItemGoodsViewModel>(BR.item, R.layout.item_goods)
    }.toItemBinding()

    private val deserveList = AsyncDiffObservableList(itemDiffOf<ItemGoodsViewModel> { oldItem, newItem ->
        oldItem.goodsBean.itemid == newItem.goodsBean.itemid
    })

    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val item = list[position]
            if (item is ItemGoodsHeaderViewModel)
                return 2
            return 1
        }
    }

    val headerViewModel = ItemGoodsHeaderViewModel(this)
    val list = MergeObservableList<Any>()
        .insertItem(headerViewModel)
        .insertList(deserveList)

    lateinit var itemid: String
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
            showTradeDetail(_activity, itemid)
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
    fun loadRecommendData() {
        NetRepositoryImpl.getRecommend(itemId = itemid.toLong())
            .bindToException()
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .subscribe({
                deserveList.update(it.data.map { item ->
                    ItemGoodsViewModel(this, item)
                })
            }, {
                handleThrowable(it)
            })
    }
}
