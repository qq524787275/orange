package com.zhuzichu.orange.sort

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.bean.SortBean
import com.zhuzichu.orange.search.SearchFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 15:12
 */
class ItemImageViewModel(viewModel: SortViewModel, var info: SortBean.Data.Info) :
    ItemViewModel<SortViewModel>(viewModel) {

    val clickItem = BindingCommand<Any>(BindingAction {
        viewModel.startFragment(
            SearchFragment(),
            bundleOf(
                SearchFragment.KEYWORD to info.son_name
            )
        )
    })
}