package com.zhuzichu.orange.search.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 15:01
 */
class ItemSearchNavViewModel(
    viewModel: SearchResultViewModel,
    title: String,
    vararg sort: Int
) : ItemViewModel<SearchResultViewModel>(viewModel) {
    val color = ColorGlobal
    val title = MutableLiveData(title)
    val selected = ObservableBoolean(false)
    val position = ObservableInt(0)
    var currentSort = sort[0]
    //是否倒叙
    val isDes = ObservableBoolean(true)

    val onClickItem = BindingCommand<Any>({

        if (selected.get() && position.get() != 2) {
            return@BindingCommand
        }

        viewModel.navList.value = viewModel.navList.value?.map { item ->
            item.also {
                it.selected.set(it.position.get() == this.position.get())
            }
        }

        if (sort.size == 2) {
            if (isDes.get()) {
                currentSort = sort[0]
                isDes.set(false)
            } else {
                currentSort = sort[1]
                isDes.set(true)
            }
        }
        viewModel.pageNo = 1
        viewModel.sort = currentSort
        viewModel.showLoadingDialog()
        viewModel.loadData()

    })
}