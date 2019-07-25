package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.bean.SalesBean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 11:14
 */
class ItemHomeBannerViewModel(
    viewModel: HomeViewModel,
    var salesBean: SalesBean
) : ItemViewModel<HomeViewModel>(viewModel) {
    val onItemClick = BindingCommand<Any>({
        salesBean.itemshorttitle.toast()
    })
}