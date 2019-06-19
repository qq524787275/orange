package com.zhuzichu.orange.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.NetRepositoryImpl

class RankingViewModel(application: Application) : BaseViewModel(application) {
    private var current = 0
    private var type: Int = 1
    private var back = 10
    private var mine_id = 0

    val itemBindIndicator = itemBindingOf<ItemRankingIndicatorViewModel>(BR.item, R.layout.item_ranking_indicator)
    val listIndicator = MutableLiveData<List<ItemRankingIndicatorViewModel>>().apply {
        value = listOf(
            ItemRankingIndicatorViewModel(
                this@RankingViewModel,
                "实时爆单榜",
                "近两小时销量排行榜",
                type,
                listOf(R.mipmap.ic_ranking_tab1_white, R.mipmap.ic_ranking_tab1_blue)
            ).apply {
                isSelected.set(true)
            },
            ItemRankingIndicatorViewModel(
                this@RankingViewModel,
                "今日爆单榜",
                "今日商品销量排行榜",
                2,
                listOf(R.mipmap.ic_ranking_tab2_white, R.mipmap.ic_ranking_tab2_blue)
            ),
            ItemRankingIndicatorViewModel(
                this@RankingViewModel,
                "出单指数榜",
                "大牛真实成交指数榜",
                4,
                listOf(R.mipmap.ic_ranking_tab3_white, R.mipmap.ic_ranking_tab3_blue)
            )
        )
    }

    fun selectIndcator(item: ItemRankingIndicatorViewModel) {
        val value = listIndicator.value!!
        val index = value.indexOf(item)
        updateData(item.type)
        value[current].isSelected.set(false)
        value[index].isSelected.set(true)
        current = index
    }

    @SuppressLint("CheckResult")
    fun updateData(type: Int) {
        this.type = type
        NetRepositoryImpl.getSalersList(back, this.type, mine_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                it.data.size.toast()
            }, {
                handleThrowable(it)
            })
    }
}