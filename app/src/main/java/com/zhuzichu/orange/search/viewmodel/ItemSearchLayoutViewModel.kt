package com.zhuzichu.orange.search.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand

class ItemSearchLayoutViewModel(viewModel: SearchResultViewModel) : ItemViewModel<SearchResultViewModel>(viewModel) {
    val clickSearch = BindingCommand<Any>(BindingAction {
        viewModel.uc.clickGoSearchEvent.call()
//        val searchFragment = SearchFragment()
//        viewModel.startFragment(searchFragment, launchMode = ISupportFragment.SINGLETASK)
    })
}