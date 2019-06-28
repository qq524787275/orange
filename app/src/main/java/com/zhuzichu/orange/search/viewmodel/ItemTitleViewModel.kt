package com.zhuzichu.orange.search.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.repository.DbRepositoryImpl
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-21
 * Time: 16:32
 */
class ItemTitleViewModel(
    viewModel: SearchViewModel,
    var title: String,
    var list: AsyncDiffObservableList<Any>
) : ItemViewModel<SearchViewModel>(viewModel) {
    val isHistory = ObservableBoolean(title == "历史记录")


    val onDeleteAll = BindingCommand<Any>( {
        viewModel.viewModelScope.launch {
            DbRepositoryImpl.deleteSearchHistory(list.map {
                (it as ItemHistoryViewModel).searchHistory
            })
        }
    })
}