package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.flutter.MainFlutterFragment
import com.zhuzichu.orange.home.fragment.RankingFragment

class ItemNavigationViewModel(
    viewModel: HomeViewModel,
    var title: String,
    var iconId: Int
) : ItemViewModel<HomeViewModel>(viewModel) {
    val color = ColorGlobal
    val onClickNavigation = BindingCommand<Any>({
        when (title) {
            "榜单" -> {
                viewModel.startFragment(RankingFragment())
            }
            "达人说" -> {
                viewModel.startFragment(RankingFragment())
            }
            "flutter" -> {
                viewModel.startFragment(MainFlutterFragment())
//                MainFlutterActivity.start(viewModel._activity)
            }
            else -> {
                "暂未开发".toast()
            }
        }
    })
}