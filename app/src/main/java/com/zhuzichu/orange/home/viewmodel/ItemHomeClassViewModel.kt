package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-04
 * Time: 16:47
 */
class ItemHomeClassViewModel(
    viewModel: HomeViewModel,
    var title: String,
    var background: Int
) : ItemViewModel<HomeViewModel>(viewModel) {
    val color = ColorGlobal
}