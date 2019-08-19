package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.search.fragment.SearchFragment
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 14:14
 */
class SearchResultPlusViewModel(application: Application) : BaseViewModel(application) {
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val finishRefreshing = SingleLiveEvent<Any>()
        val finishLoadmore = SingleLiveEvent<Any>()
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val color = ColorGlobal
    private val pageSize = 20L
    private var pageNo = 1L
    private var keyWord = ""
    val spanSize = ObservableInt(2)
    val navItemBind = itemBindingOf<ItemSearchNavViewModel>(BR.item, R.layout.item_search_nav)
    val navList = MutableLiveData<List<ItemSearchNavViewModel>>().also {
        it.value = listOf(
            ItemSearchNavViewModel(this, "综合"),
            ItemSearchNavViewModel(this, "销量"),
            ItemSearchNavViewModel(this, "价格")
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
        AsyncDiffObservableList(itemDiffOf<ItemResultPlusViewModel> { oldItem, newItem -> oldItem.itemid == newItem.itemid })
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_search_result_plus)


    @SuppressLint("CheckResult")
    fun loadData(keyword: String) {
        this.keyWord = keyword
        NetRepositoryImpl.searchGoods(pageSize, pageNo, keyword)
            .bindToException()
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .subscribe(
                {
                    val data = it.data.tbk_dg_material_optional_response.result_list.map_data.map { item ->
                        ItemResultPlusViewModel(this, item, spanSize)
                    }
                    if (pageNo == 1L) {
                        list.update(data)
                        uc.finishRefreshing.call()
                    } else {
                        list.update(list.plus(data))
                    }
                    if (data.size < pageSize) {
                        uc.finishLoadMoreWithNoMoreData.call()
                    } else {
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
        pageNo = 1L
        loadData(this.keyWord)
    })
    val onLoadMoreCommand = BindingCommand<Any>({
        pageNo = pageNo.inc()
        loadData(this.keyWord)
    })

    val onErrorCommand = BindingCommand<Any>({
        loadData(this.keyWord)
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