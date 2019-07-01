package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.showTradeDetail
import com.zhuzichu.orange.bean.SalesBean
import com.zhuzichu.orange.checkLogin

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-20
 * Time: 18:01
 */
class ItemRankingViewModel(
    viewModel: RankingViewModel,
    var salesBean: SalesBean,
    var top: String
) : ItemViewModel<RankingViewModel>(viewModel) {
    val clickItem = BindingCommand<Any>( {
        checkLogin {
            showTradeDetail(viewModel._activity, salesBean.itemid)
        }
    })
}