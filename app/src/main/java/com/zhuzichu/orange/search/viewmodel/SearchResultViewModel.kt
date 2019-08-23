package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.DbRepositoryImpl
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.search.fragment.SearchFragment
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 14:14
 * * 0.综合（最新），1.券后价(低到高)，2.券后价（高到低），3.券面额，4.销量，5.佣金比例，6.券面额（低到高），7.月销量（低到高），8.佣金比例（低到高），9.全天销量（高到低），10全天销量（低到高），11.近2小时销量（高到低），12.近2小时销量（低到高），13.优惠券领取量（高到低），14.好单库指数（高到低）
 */
class SearchResultViewModel(application: Application) : BaseViewModel(application) {
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val finishRefreshing = SingleLiveEvent<Any>()
        val finishLoadmore = SingleLiveEvent<Any>()
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val color = ColorGlobal
    private var back = 20
    private var cid = 0
     var sort = 0
    var minId = 1
    private var lastMinId = 1
    internal lateinit var keyWord: String
    val spanSize = ObservableInt(2)
    val navItemBind = itemBindingOf<ItemSearchNavViewModel>(BR.item, R.layout.item_search_nav)
    val navList = MutableLiveData<List<ItemSearchNavViewModel>>().also {
        it.value = listOf(
            ItemSearchNavViewModel(this, "综合", 0),
            ItemSearchNavViewModel(this, "销量", 4),
            ItemSearchNavViewModel(this, "价格", 1, 2)
        ).apply {
            this.forEachIndexed { index, item ->
                if (index == 0)
                    item.selected.set(true)
                item.position.set(index)
            }
        }
    }

    val viewState = ObservableInt(MultiStateView.VIEW_STATE_LOADING)
    val list =
        AsyncDiffObservableList(itemDiffOf<ItemResultViewModel> { oldItem, newItem -> oldItem.itemid == newItem.itemid })
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_search_result)


    @SuppressLint("CheckResult")
    fun loadData() {
        viewModelScope.launch { DbRepositoryImpl.addSearchHistory(keyWord) }
        NetRepositoryImpl.searchShop(keyWord, back, sort, cid, minId)
            .bindToException()
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .subscribe(
                {
                    val data = it.data.map { item ->
                        ItemResultViewModel(this, item, spanSize)
                    }
                    if (minId == 1) {
                        list.update(data)
                        uc.finishRefreshing.call()
                        hideLoadingDialog()
                    } else {
                        list.update(list.plus(data))
                        viewState.set(MultiStateView.VIEW_STATE_CONTENT)
                    }
                    if (data.size < back) {
                        uc.finishLoadMoreWithNoMoreData.call()
                    } else {
                        lastMinId = it.min_id
                        uc.finishLoadmore.call()
                    }
                    viewState.set(MultiStateView.VIEW_STATE_CONTENT)
                },
                {
                    handleThrowable(it)
                    viewState.set(MultiStateView.VIEW_STATE_ERROR)
                }
            )
    }


    val onRefreshCommand = BindingCommand<Any>({
        minId = 1
        loadData()
    })
    val onLoadMoreCommand = BindingCommand<Any>({
        minId = lastMinId
        loadData()
    })

    val onErrorCommand = BindingCommand<Any>({
        loadData()
    })


    val onClickSearchLayout = BindingCommand<Any>({
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })

    val onClickSpanSizie = BindingCommand<Any>({
        spanSize.set(
            if (spanSize.get() == 1) 2 else 1
        )
    })

}