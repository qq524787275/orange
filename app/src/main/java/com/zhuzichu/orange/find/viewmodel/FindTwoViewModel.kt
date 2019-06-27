package com.zhuzichu.orange.find.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.NetRepositoryImpl
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 10:01
 */
class FindTwoViewModel(application: Application) : BaseViewModel(application) {
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
                it.data.size.toast()
                list.update(it.data.map { item ->
                    ItemTwoViewModel(
                        this@FindTwoViewModel
                        , item
                        , itemBindingOf(BR.item, R.layout.item_find_two_item)
                        , item.item_data.mapIndexed { index, itemdData ->
                            if(index!=4)
                            itemdData.itempic = itemdData.itempic.plus("_310x310.jpg")
                            ItemTwoItemViewModel(this@FindTwoViewModel, itemdData)
                        })
                })
                min_id = it.min_id
            }, {
                handleThrowable(it)
            })
    }
}