package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.http.exception.ExceptionHandle
import com.zhuzichu.mvvm.http.exception.ResponseThrowable
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.bean.SearchBean
import com.zhuzichu.orange.repository.DbRepositoryImpl
import com.zhuzichu.orange.repository.NetRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchFragment
import kotlinx.coroutines.launch
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
    private var back = 10
    private var cid = 0
    private var sort = 0
    private var min_id = 1
    private var keyword = ""
    private var currentIndicator = 0
    val spanSize = ObservableInt(2)
    val viewState = ObservableInt(MultiStateView.VIEW_STATE_LOADING)
    //封装一个界面发生改变的观察者
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        //下拉刷新完成
        val finishRefreshing = SingleLiveEvent<Any>()
        //上拉加载完成
        val finishLoadmore = SingleLiveEvent<Any>()
        //上拉加载完成 并且到最后一数据
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()

        val onSpanSizeChangeEvent = SingleLiveEvent<Int>()

        val clickItemResultEvent = SingleLiveEvent<SearchBean>()
    }

    val onChangeSpanSize = BindingCommand<Any>(BindingAction {
        uc.onSpanSizeChangeEvent.call()
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
    private val liveData = MutableLiveData<List<ItemResultViewModel>>().apply {
        value = ArrayList()
    }
    val list: LiveData<List<Any>> = map(liveData) { input ->
        val list = ArrayList<Any>(input.size)
        list.addAll(input)
        list
    }

    val pagedList: LiveData<PagedList<Any>> =
        LivePagedListBuilder(object : DataSource.Factory<Int, Any>() {
            override fun create(): DataSource<Int, Any> =
                object : ItemKeyedDataSource<Int,Any>() {
                    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Any>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Any>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Any>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun getKey(item: Any): Int {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        }, back).build()

    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemResultViewModel && newItem is ItemResultViewModel) {
                oldItem.searchBean.itemid == newItem.searchBean.itemid
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }

    val onRefreshCommand = BindingCommand<Any>(BindingAction {
        min_id = 1
        searchShop(this.keyword)
    })
    val onLoadMoreCommand = BindingCommand<Any>(BindingAction {
        searchShop(this.keyword)
    })

    val clickSearchLayout = BindingCommand<Any>(BindingAction {
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })

    fun searchShop(keyword: String) {
        this.keyword = keyword
        viewModelScope.launch { DbRepositoryImpl.addSearchHistory(keyword) }
        NetRepositoryImpl.searchShop(this.keyword, back, sort, cid, min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map {
                if (min_id == 1) {
                    liveData.value = ArrayList()
                    uc.finishRefreshing.call()
                }
                min_id = it.min_id
                val list = mutableListOf<ItemResultViewModel>()
                it.data.forEach { item ->
                    item.itempic.plus("_310x310.jpg")
                    list.add(ItemResultViewModel(this, item, spanSize))
                }
                list
            }
            .subscribe({
                if (it.size < back) {
                    uc.finishLoadMoreWithNoMoreData.call()
                } else {
                    uc.finishLoadmore.call()
                }
                liveData.value = liveData.value!! + it
                viewState.set(MultiStateView.VIEW_STATE_CONTENT)
            }, {
                if (it is ResponseThrowable) {
                    if (it.code == ExceptionHandle.ERROR.NO_DATA && liveData.value?.size == 0) {
                        viewState.set(MultiStateView.VIEW_STATE_EMPTY)
                    } else {
                        viewState.set(MultiStateView.VIEW_STATE_CONTENT)
                    }
                }
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

    fun changeSpanSize() {
        spanSize.set(
            if (spanSize.get() == 1) 2 else 1
        )
    }
}