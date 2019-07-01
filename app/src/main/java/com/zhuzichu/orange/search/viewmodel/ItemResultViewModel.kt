package com.zhuzichu.orange.search.viewmodel


import androidx.core.os.bundleOf
import androidx.databinding.ObservableInt
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.bean.SearchBean
import com.zhuzichu.orange.shopdetail.fragment.ShopDetailFragment


class ItemResultViewModel(viewModel: SearchResultViewModel, var searchBean: SearchBean, val spanSize: ObservableInt) :
    ItemViewModel<SearchResultViewModel>(viewModel) {

    val clickItem = BindingCommand<Any>({
        viewModel.startFragment(
            ShopDetailFragment(), bundleOf(
                ShopDetailFragment.ITEMID to searchBean.itemid,
                ShopDetailFragment.TYPE to searchBean.shoptype
            )
        )
    })

}

