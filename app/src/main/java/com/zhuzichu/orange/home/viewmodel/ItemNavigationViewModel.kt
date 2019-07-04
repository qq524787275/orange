package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.home.fragment.RankingFragment

class ItemNavigationViewModel(
    viewModel: HomeViewModel,
    var title: String,
    var iconId: Int
) : ItemViewModel<HomeViewModel>(viewModel) {
    val onClickNavigation = BindingCommand<Any>({
        when (title) {
            "榜单" -> {
                viewModel.startFragment(RankingFragment())
            }
            else -> {
                "暂未开发".toast()
            }
        }
    })
}