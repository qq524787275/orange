package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.bean.TalentcatBean

class ItemTalentBannerViewModel(
    viewModel: BaseViewModel,
    var data: TalentcatBean.Topdata
) : ItemViewModel<BaseViewModel>(viewModel) {
    val onItemClick = BindingCommand<Any>({
        data.name.toast()
    })
}