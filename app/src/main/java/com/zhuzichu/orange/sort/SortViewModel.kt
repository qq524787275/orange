package com.zhuzichu.orange.sort

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import com.secretk.move.RepositoryImpl
import com.zhuzichu.mvvm.BR
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.R
import io.reactivex.Flowable


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:42
 * @see R.layout.fragment_sort
 */
@SuppressLint("CheckResult")
class SortViewModel(application: Application) : BaseViewModel(application) {

    var current = 0
    val leftItemBind = itemBindingOf<Any>(BR.item, R.layout.item_sort_left)
    private val leftLiveData = MutableLiveData<List<ItemLeftViewModel>>().apply { value = ArrayList() }
    val leftList: LiveData<List<Any>> = Transformations.map(leftLiveData) { it }
    val leftDiff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemLeftViewModel && newItem is ItemLeftViewModel) {
                oldItem.sortBean.cid == newItem.sortBean.cid
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }

    val rightItemBind = itemBindingOf<Any>(BR.item, R.layout.item_sort_right)
    private val rightLiveData = MutableLiveData<List<SortItemRightViewModel>>().apply { value = ArrayList() }
    val rightList: LiveData<List<Any>> = Transformations.map(rightLiveData) { it }
    val rightDiff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }

    override fun onLazyInitView() {
        loadShopSort()
    }

    private fun loadShopSort() {
        RepositoryImpl.getShopSort()
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .flatMap {
                Flowable.fromArray(it.general_classify)
            }
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                ItemLeftViewModel(this, it)
            }
            .toList()
            .subscribe({
                leftLiveData.value = it
                updateRight(it[current])
                showContent()
            }, {
                showError()
                handleThrowable(it)
            })
    }

    private fun updateRight(itemLeftViewModel: ItemLeftViewModel) {
        itemLeftViewModel.isSelected.set(true)
        Flowable.fromArray(itemLeftViewModel.sortBean.data)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                SortItemRightViewModel(this, it)
            }
            .toList()
            .subscribe({
                rightLiveData.value = it
            }, {
                handleThrowable(it)
            })
    }

    fun selectLeftItem(itemLeftViewModel: ItemLeftViewModel) {
        itemLeftViewModel.isSelected.set(true)
        leftLiveData.value?.get(current)?.isSelected?.set(false)
        current = leftLiveData.value?.indexOf(itemLeftViewModel)!!
        updateRight(itemLeftViewModel)
    }
}