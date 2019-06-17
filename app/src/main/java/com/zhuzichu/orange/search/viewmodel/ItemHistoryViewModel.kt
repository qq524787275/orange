package com.zhuzichu.orange.search.viewmodel

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.db.SearchHistory
import com.zhuzichu.orange.repository.DbRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchResultFragment
import kotlinx.coroutines.launch

class ItemHistoryViewModel(viewModel: SearchViewModel, var searchHistory: SearchHistory) :
    ItemViewModel<SearchViewModel>(viewModel) {

    val clickItem = BindingCommand<Any>(BindingAction {
        viewModel.hideSoftKeyBoard()
        viewModel.startFragment(
            SearchResultFragment(),
            bundleOf(
                SearchResultFragment.KEYWORD to searchHistory.keyWord
            )
        )
    })

    val onClickDelete = BindingCommand<Any>(BindingAction {
        viewModel.viewModelScope.launch {
            DbRepositoryImpl.deleteSearchHistory(listOf(searchHistory))
        }
    })

}