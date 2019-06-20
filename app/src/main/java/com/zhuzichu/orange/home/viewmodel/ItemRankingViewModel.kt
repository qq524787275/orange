package com.zhuzichu.orange.home.viewmodel

import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.orange.bean.SalesBean

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
    var index: Int
) : ItemViewModel<RankingViewModel>(viewModel) {

}