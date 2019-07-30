package com.zhuzichu.orange.search.viewmodel


import androidx.core.os.bundleOf
import androidx.databinding.ObservableInt
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.bean.SearchBean
import com.zhuzichu.orange.shopdetail.fragment.ShopDetailFragment


class ItemResultViewModel(viewModel: SearchResultViewModel, var searchBean: SearchBean, val spanSize: ObservableInt) :
    ItemViewModel<SearchResultViewModel>(viewModel) {

    val onClickItem = BindingCommand<Any>({
        viewModel.startFragment(
            ShopDetailFragment(), bundleOf(
                ShopDetailFragment.ITEMID to searchBean.itemid,
                ShopDetailFragment.TYPE to searchBean.shoptype,
                ShopDetailFragment.ITEMENDPRICE to searchBean.itemendprice,
                ShopDetailFragment.ITEMPRICE to searchBean.itemprice
            )
        )
    })
}

