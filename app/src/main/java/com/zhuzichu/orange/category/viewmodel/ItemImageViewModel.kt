package com.zhuzichu.orange.category.viewmodel

import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
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
    viewModel: CategoryViewModel,
    val category: CategoryBean.Data.Info
) :
    ItemViewModel<CategoryViewModel>(viewModel) {
    val name = ObservableField(category.son_name)
    val image = ObservableField(category.imgurl)
    val color = ColorGlobal
    val clickItem = BindingCommand<Any>({
        viewModel.startFragment(
            SearchResultFragment(),
            bundleOf(
                SearchResultFragment.KEYWORD to category.son_name
            )
        )
    })
}