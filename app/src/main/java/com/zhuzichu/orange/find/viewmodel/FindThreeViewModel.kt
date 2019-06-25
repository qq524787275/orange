package com.zhuzichu.orange.find.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.Constants
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
class FindThreeViewModel(application: Application) : BaseViewModel(application) {
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemThreeViewModel && newItem is ItemThreeViewModel) {
                oldItem.subjectBean.id == newItem.subjectBean.id
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
    }

    val list = AsyncDiffObservableList(diff)

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_find_three)


    @SuppressLint("CheckResult")
    fun loadSubjectData() {
        NetRepositoryImpl.getSubjectList()
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map {
                it.data.map { item ->
                    item.app_image = Constants.IMAGE_URL.plus(item.app_image)
                    ItemThreeViewModel(this@FindThreeViewModel, item)
                }
            }
            .subscribe {
                it.size.toast()
                list.update(it)
            }
    }
}