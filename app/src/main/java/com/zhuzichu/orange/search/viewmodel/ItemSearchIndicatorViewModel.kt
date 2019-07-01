package com.zhuzichu.orange.search.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-18
 * Time: 16:53
 */
class ItemSearchIndicatorViewModel(viewModel: SearchResultViewModel,var title: String,var sort: List<Int>) :
    ItemViewModel<SearchResultViewModel>(viewModel) {
    var isSelected = ObservableBoolean(false)
    var currentSort = ObservableInt(sort[0])

    var clickItem = BindingCommand<Any>( {
        //todo 去除魔法数
        if (isSelected.get() && sort.size == 1) {
            return@BindingCommand
        }

        if (sort.size == 1) {
            viewModel.selectIndcatorItem(this, sort[0])
            currentSort.set(sort[0])
        } else if (sort.size == 2) {

            if (currentSort.get() == sort[0]) {
                currentSort.set(sort[1])
                viewModel.selectIndcatorItem(this, sort[1])
            } else {
                currentSort.set(sort[0])
                viewModel.selectIndcatorItem(this, sort[0])
            }
        }
    })
}