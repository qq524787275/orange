package com.zhuzichu.orange.home.viewmodel

import androidx.databinding.ObservableBoolean
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand

class ItemRankingIndicatorViewModel(
    viewModel: RankingViewModel,
    var title: String,
    var desc: String,
    var type: Int,
    val icons: List<Int>
) :
    ItemViewModel<RankingViewModel>(viewModel) {

    var isSelected = ObservableBoolean(false)

    var clickItem = BindingCommand<Any>( {
        //todo 去除魔法数
        viewModel.selectIndcator(this)
    })
}