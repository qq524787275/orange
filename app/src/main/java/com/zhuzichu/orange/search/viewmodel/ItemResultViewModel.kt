package com.zhuzichu.orange.search.viewmodel


import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.bean.SearchBean


class ItemResultViewModel(viewModel: SearchResultModel, var searchBean: SearchBean) :
    ItemViewModel<SearchResultModel>(viewModel) {

    val clickItem = BindingCommand<Any>(BindingAction {
        searchBean.itemshorttitle.toast()
    })

}

