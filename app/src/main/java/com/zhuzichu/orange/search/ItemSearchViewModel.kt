package com.zhuzichu.orange.search


import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.bean.SearchBean


class ItemSearchViewModel(viewModel: SearchViewModel, var searchBean: SearchBean) :
    ItemViewModel<SearchViewModel>(viewModel) {

    val clickItem = BindingCommand<Any>(BindingAction {
        searchBean.itemshorttitle.toast()
    })

}

