package com.zhuzichu.orange.search

import android.annotation.SuppressLint
import android.app.Application
import android.text.Html
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import com.secretk.move.RepositoryImpl
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
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
class SearchViewModel(application: Application) : BaseViewModel(application) {

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_search)
    private val liveData = MutableLiveData<List<ItemSearchViewModel>>().apply {
        value = ArrayList()
    }
    val list: LiveData<List<Any>> = Transformations.map(liveData) { it }
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemSearchViewModel && newItem is ItemSearchViewModel) {
                oldItem.searchBean.itemid == newItem.searchBean.itemid
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }

    fun searchShop(keyWord: String) {
        RepositoryImpl.searchShop(keyWord, 10, 0, 0, 1)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map { it.data }
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                it.itempic.plus("_310x310.jpg")
                ItemSearchViewModel(this, it)
            }
            .toList()
            .subscribe({
                liveData.value = it
            }, {
                handleThrowable(it)
            })
    }
}