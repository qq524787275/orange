package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.http.exception.ExceptionHandle
import com.zhuzichu.mvvm.http.exception.ResponseThrowable
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.DbRepositoryImpl
import com.zhuzichu.orange.repository.NetRepositoryImpl
import com.zhuzichu.orange.sort.viewmodel.ItemTitleViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 14:38
 */
@SuppressLint("CheckResult")
class SearchResultViewModel(application: Application) : BaseViewModel(application) {
    private var back = 10
    private var cid = 0
    private var sort = 0
    private var min_id = 1
    private var keyword = ""
    //封装一个界面发生改变的观察者
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        //下拉刷新完成
        val finishRefreshing = SingleLiveEvent<Any>()
        //上拉加载完成
        val finishLoadmore = SingleLiveEvent<Any>()
        //上拉加载完成 并且到最后一数据
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val itemBind = OnItemBindClass<Any>().apply {
        map<ItemSearchLayoutViewModel>(BR.item, R.layout.item_search_layout)
        map<ItemResultViewModel>(BR.item, R.layout.item_search_result)
    }
    private val liveData = MutableLiveData<List<ItemResultViewModel>>().apply {
        value = ArrayList()
    }
    val list: LiveData<List<Any>> = map(liveData) { input ->
        val list = ArrayList<Any>(input.size + 1)
        list.add(ItemSearchLayoutViewModel(this))
        list.addAll(input)
        list
    }

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


    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            if (position == 0)
                return 2
            return 1
        }
    }

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
                    list.add(ItemResultViewModel(this, item))
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
                showContent()
            }, {
                if (it is ResponseThrowable) {
                    if (it.code == ExceptionHandle.ERROR.NO_DATA && liveData.value?.size == 0) {
                        showEmpty()
                    } else {
                        showContent()
                    }
                }
                handleThrowable(it)
                uc.finishLoadmore.call()
            })
    }
}