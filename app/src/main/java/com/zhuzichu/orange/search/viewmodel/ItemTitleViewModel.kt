package com.zhuzichu.orange.search.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-21
 * Time: 16:32
 */
class ItemTitleViewModel(
    viewModel: SearchViewModel,
    var title: String
) : ItemViewModel<SearchViewModel>(viewModel) {
}