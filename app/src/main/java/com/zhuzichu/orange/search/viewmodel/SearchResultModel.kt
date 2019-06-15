package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import com.secretk.move.RepositoryImpl
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.http.exception.ExceptionHandle
import com.zhuzichu.mvvm.http.exception.ResponseThrowable
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import io.reactivex.Flowable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 14:38
 */
@SuppressLint("CheckResult")
class SearchResultModel(application: Application) : BaseViewModel(application) {
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

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_search)
    private val liveData = MutableLiveData<List<ItemResultViewModel>>().apply {
        value = ArrayList()
    }
    val list: LiveData<List<Any>> = Transformations.map(liveData) { it }
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


    fun searchShop(keyword: String) {
        this.keyword = keyword
        RepositoryImpl.searchShop(this.keyword, back, sort, cid, min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map {
                if (min_id == 1) {
                    liveData.value = ArrayList()
                    uc.finishRefreshing.call()
                }
                min_id = it.min_id
                it.data
            }
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                it.itempic.plus("_310x310.jpg")
                ItemResultViewModel(this, it)
            }
            .toList()
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