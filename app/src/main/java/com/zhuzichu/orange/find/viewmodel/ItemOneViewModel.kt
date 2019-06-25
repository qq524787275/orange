package com.zhuzichu.orange.find.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.orange.bean.SelectedItemBean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 11:40
 */
class ItemOneViewModel(
    viewModel: FindOneViewModel,
    var selectedItemBean: SelectedItemBean
) : ItemViewModel<FindOneViewModel>(viewModel) {
}