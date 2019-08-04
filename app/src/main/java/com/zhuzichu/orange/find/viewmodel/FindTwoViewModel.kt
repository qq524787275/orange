package com.zhuzichu.orange.find.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 10:01
 */
class FindTwoViewModel(application: Application) : BaseViewModel(application) {
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val finishRefreshing = SingleLiveEvent<Any>()
        val finishLoadmore = SingleLiveEvent<Any>()
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val onRefreshCommand = BindingCommand<Any>({
        min_id = 1
        loadSubjefctHostData()
    })
    val onLoadMoreCommand = BindingCommand<Any>({
        loadSubjefctHostData()
    })

    private var min_id = 1
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemTwoViewModel && newItem is ItemTwoViewModel) {
                oldItem.subjectHotBean.subject_id == newItem.subjectHotBean.subject_id
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
    }
    val list = AsyncDiffObservableList(diff)
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_find_two)

    @SuppressLint("CheckResult")
    fun loadSubjefctHostData() {
        NetRepositoryImpl.getSubjectHotList(min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val data = it.data
                if (data.isEmpty() && min_id == 1) {
                    showEmpty()
                    return@subscribe
                }
                val list = data.map { item ->
                    item.copy_text = item.copy_text.replace("&lt;br&gt;", "\t\n")
                    ItemTwoViewModel(
                        this@FindTwoViewModel
                        , item
                        , itemBindingOf(BR.item, R.layout.item_find_two_item)
                        , item.item_data.mapIndexed { index, itemdData ->
                            if (index != 4)
                                itemdData.itempic = itemdData.itempic.plus("_310x310.jpg")
                            ItemTwoItemViewModel(this@FindTwoViewModel, itemdData)
                        })
                }
                if (min_id == 1) {
                    this.list.update(list)
                    uc.finishRefreshing.call()
                } else {
                    this.list.update(this.list + list)
                }
                if (list.size < 10) {
                    uc.finishLoadMoreWithNoMoreData.call()
                } else {
                    uc.finishLoadmore.call()
                }
                showContent()
                min_id = it.min_id
            }, {
                showError()
                handleThrowable(it)
                uc.finishLoadmore.call()
            })
    }
}