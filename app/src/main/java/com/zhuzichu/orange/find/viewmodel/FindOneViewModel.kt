package com.zhuzichu.orange.find.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
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
class FindOneViewModel(application: Application) : BaseViewModel(application) {
    var min_id = 1
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemOneViewModel && newItem is ItemOneViewModel) {
                oldItem.selectedItemBean.edit_id == newItem.selectedItemBean.edit_id
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
    }
    val list = AsyncDiffObservableList(diff)
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_find_one)


    @SuppressLint("CheckResult")
    fun loadSelectItemList() {

        NetRepositoryImpl.getSelectedItemList(min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map {
                it.data.map { item ->
                    item.copy_content = item.copy_content.replace("&lt;br&gt;", "\t\n")
                    item.itempic.map { url ->
                        url.plus("_200x200.jpg")
                    }
                    ItemOneViewModel(this@FindOneViewModel, item)
                }
            }
            .subscribe {
                list.update(it)
            }
    }
}