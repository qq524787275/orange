package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.mvvm.db.SearchHistory
import com.zhuzichu.mvvm.repository.DbRepositoryImpl
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchResultFragment
import io.reactivex.Flowable
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

@SuppressLint("CheckResult")
class SearchViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemHistoryViewModel && newItem is ItemHistoryViewModel) {
                oldItem.searchHistory.keyWord == newItem.searchHistory.keyWord
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
    }

    private val mutableLiveData = AsyncDiffObservableList(diff)

    private val hotLiveData = AsyncDiffObservableList(diff)

    val list = MergeObservableList<Any>()
        .insertItem(ItemTitleViewModel(this, "历史记录", mutableLiveData))
        .insertList(mutableLiveData)
        .insertItem(ItemTitleViewModel(this, "热搜记录", hotLiveData))
        .insertList(hotLiveData)

    val itemBind = OnItemBindClass<Any>().apply {
        map<ItemHistoryViewModel>(BR.item, R.layout.item_search_history)
        map<ItemTitleViewModel>(BR.item, R.layout.item_search_history_title)
    }


    val keyWord = MutableLiveData<String>()

    val clickSearch = BindingCommand<Any>({
        if (keyWord.value.isNullOrBlank()) {
            "请输入有数据".toast()
            return@BindingCommand
        }
        hideSoftKeyBoard()
        startFragment(
            SearchResultFragment(),
            bundleOf(
                SearchResultFragment.KEYWORD to keyWord.value
            )
        )
    })

    @SuppressLint("CheckResult")
    fun loadHistoryData() {
        DbRepositoryImpl.getSearchHistoryList()
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .map {
                val list = mutableListOf<Any>()
                it.forEach { item ->
                    list.add(ItemHistoryViewModel(this, item, false))
                }
                list
            }
            .subscribe({
                mutableLiveData.update(it as List<Any>?)
            }, {
                handleThrowable(it)
            })
    }

    fun loadHotKeyData() {
        NetRepositoryImpl.getHotKeyList()
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .map { it.data }
            .flatMap { Flowable.fromIterable(it) }
            .take(10)
            .toList()
            .map {
                val list = mutableListOf<Any>()
                it.forEach { item ->
                    list.add(ItemHistoryViewModel(this, SearchHistory(item.keyword), true))
                }
                list
            }
            .subscribe({
                hotLiveData.update(it)
            }, {
                handleThrowable(it)
            })
    }
}