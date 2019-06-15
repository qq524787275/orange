package com.zhuzichu.orange.search.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.DbRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchResultFragment

class SearchViewModel(application: Application) : BaseViewModel(application) {
    private val mutableLiveData = MutableLiveData<List<ItemHistoryViewModel>>().apply {
        value = ArrayList()
    }

    val list: LiveData<Any> = map(mutableLiveData) { it }

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_search_history)

    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemHistoryViewModel && newItem is ItemHistoryViewModel) {
                oldItem.searchHistory.keyWord == newItem.searchHistory.keyWord
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
    }


    val keyWord = MutableLiveData<String>()

    val clickSearch = BindingCommand<Any>(BindingAction {

        if (keyWord.value.isNullOrBlank()) {
            "请输入有数据".toast()
            return@BindingAction
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
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .map {
                val list = mutableListOf<ItemHistoryViewModel>()
                it.forEach { item ->
                    list.add(ItemHistoryViewModel(this, item))
                }
                list
            }
            .subscribe({
                mutableLiveData.value = it
            }, {
                handleThrowable(it)
            })
    }
}