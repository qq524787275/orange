package com.zhuzichu.orange.sort.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.bean.SortBean
import com.zhuzichu.orange.search.fragment.SearchResultFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 15:12
 */
class ItemImageViewModel
    (
    viewModel: SortViewModel,
    var category: CategoryBean
) :
    ItemViewModel<SortViewModel>(viewModel) {
    val color = ColorGlobal
    val clickItem = BindingCommand<Any>({
        viewModel.startFragment(
            SearchResultFragment(),
            bundleOf(
                SearchResultFragment.KEYWORD to category.name
            )
        )
    })
}