package com.zhuzichu.orange.find.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.orange.bean.SubjectHotBean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-27
 * Time: 18:08
 */
class ItemTwoItemViewModel(
    viewModel: FindTwoViewModel,
    var itemData: SubjectHotBean.ItemData
) : ItemViewModel<FindTwoViewModel>(viewModel) {
}