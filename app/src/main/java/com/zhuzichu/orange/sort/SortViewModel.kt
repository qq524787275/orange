package com.zhuzichu.orange.sort

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.secretk.move.RepositoryImpl
import com.zhuzichu.mvvm.BR
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.R
import io.reactivex.Flowable
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass


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


    val rightItemBind = OnItemBindClass<Any>().apply {
        map<ItemImageViewModel>(BR.item, R.layout.item_sort_right_image)
        map<ItemTitleViewModel>(BR.item, R.layout.item_sort_right_title)
    }
    val rightList = MutableLiveData<List<Any>>().apply { value = ArrayList() }
    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val list = rightList.value
            if (list?.get(position) is ItemTitleViewModel) {
                return 3
            }
            return 1
        }
    }

    fun loadShopSort() {
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
        val list = ArrayList<Any>()
        itemLeftViewModel.isSelected.set(true)
        Flowable.fromArray(itemLeftViewModel.sortBean.data)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                list.add(ItemTitleViewModel(this, it))
                it.info
            }
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                list.add(ItemImageViewModel(this, it))
                it
            }
            .toList()
            .subscribe({
                rightList.value = list
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