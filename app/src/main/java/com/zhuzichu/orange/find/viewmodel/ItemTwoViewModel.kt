package com.zhuzichu.orange.find.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.SubjectHotBean
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-27
 * Time: 16:58
 */
class ItemTwoViewModel(
    viewModel: FindTwoViewModel,
    var subjectHotBean: SubjectHotBean,
    var itemBind: ItemBinding<Any>,
    var data: List<ItemTwoItemViewModel>
) : ItemViewModel<FindTwoViewModel>(viewModel) {
    var list = MutableLiveData<Any>().apply {
        value = data
    }
}