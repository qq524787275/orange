package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-04
 * Time: 16:47
 */
class ItemHomeClassViewModel(
    viewModel: HomeViewModel,
    var title: String
) : ItemViewModel<HomeViewModel>(viewModel) {

}