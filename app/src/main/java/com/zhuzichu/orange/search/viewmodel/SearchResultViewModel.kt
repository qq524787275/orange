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
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.itemDiff
import com.zhuzichu.orange.repository.DbRepositoryImpl
import com.zhuzichu.orange.repository.NetRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchFragment
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 14:38
 * 0.综合（最新），1.券后价(低到高)，2.券后价（高到低），3.券面额，4.销量，5.佣金比例，6.券面额（低到高），7.月销量（低到高），8.佣金比例（低到高），9.全天销量（高到低），10全天销量（低到高），11.近2小时销量（高到低），12.近2小时销量（低到高），13.优惠券领取量（高到低），14.好单库指数（高到低）
 */
@SuppressLint("CheckResult")
class SearchResultViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    private var back = 10
    private var cid = 0
    private var sort = 0
    private var min_id = 1
    private var keyword = ""
    private var currentIndicator = 0
    val spanSize = ObservableInt(2)
    val viewState = ObservableInt(MultiStateView.VIEW_STATE_LOADING)
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val finishRefreshing = SingleLiveEvent<Any>()
        val finishLoadmore = SingleLiveEvent<Any>()
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val onErrorCommand = BindingCommand<Any>({
        searchShop(this.keyword)
    })

    val onChangeSpanSize = BindingCommand<Any>({
        spanSize.set(
            if (spanSize.get() == 1) 2 else 1
        )
    })

    val itemBind = OnItemBindClass<Any>().apply {
        map<ItemResultViewModel>(BR.item, R.layout.item_search_result)
    }.toItemBinding()

    val itemBindIndicator = itemBindingOf<ItemSearchIndicatorViewModel>(BR.item, R.layout.item_search_indicator)
    val listIndicator = MutableLiveData<List<ItemSearchIndicatorViewModel>>().apply {
        value = listOf(
            //todo 去除魔法数
            ItemSearchIndicatorViewModel(this@SearchResultViewModel, "综合", listOf(0)).apply {
                isSelected.set(true)
            },
            ItemSearchIndicatorViewModel(this@SearchResultViewModel, "销量", listOf(4)),
            ItemSearchIndicatorViewModel(this@SearchResultViewModel, "价格", listOf(1, 2))
        )
    }

    val itemList = AsyncDiffObservableList(itemDiff<ItemResultViewModel> { oldItem, newItem ->
        oldItem.searchBean.itemid == newItem.searchBean.itemid
    })


    val onRefreshCommand = BindingCommand<Any>({
        min_id = 1
        searchShop(this.keyword)
    })
    val onLoadMoreCommand = BindingCommand<Any>({
        searchShop(this.keyword)
    })

    val clickSearchLayout = BindingCommand<Any>({
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })

    fun searchShop(keyword: String) {
        this.keyword = keyword
        viewModelScope.launch { DbRepositoryImpl.addSearchHistory(keyword) }
        NetRepositoryImpl.searchShop(this.keyword, back, sort, cid, min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val data = it.data
                if (data.isEmpty() && min_id == 1) {
                    viewState.set(MultiStateView.VIEW_STATE_EMPTY)
                    return@subscribe
                }
                val list = data.map { item ->
                    item.itempic.plus("_310x310.jpg")
                    ItemResultViewModel(this, item, spanSize)
                }
                if (min_id == 1) {
                    itemList.update(list)
                    uc.finishRefreshing.call()
                } else {
                    itemList.update(itemList + list)
                }
                if (list.size < back) {
                    uc.finishLoadMoreWithNoMoreData.call()
                } else {
                    uc.finishLoadmore.call()
                }
                viewState.set(MultiStateView.VIEW_STATE_CONTENT)
                min_id = it.min_id

            }, {
                viewState.set(MultiStateView.VIEW_STATE_ERROR)
                handleThrowable(it)
                uc.finishLoadmore.call()
            })
    }

    fun selectIndcatorItem(item: ItemSearchIndicatorViewModel, sort: Int) {
        item.isSelected.set(true)
        val position = listIndicator.value?.indexOf(item)!!
        if (currentIndicator != position) {
            listIndicator.value?.get(currentIndicator)?.isSelected?.set(false)
        }
        this.sort = sort
        min_id = 1
        searchShop(this.keyword)
        currentIndicator = position
    }
}