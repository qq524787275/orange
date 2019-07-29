package com.zhuzichu.orange.sort.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.BR
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.NetRepositoryImpl
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
    val color = ColorGlobal
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val rightRecyclerToTop = SingleLiveEvent<Any>()
    }

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
    }.toItemBinding()

    val rightList = MutableLiveData<List<Any>>().apply { value = ArrayList() }
    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val list = rightList.value
            if (list?.size!! > position)
                if (list[position] is ItemTitleViewModel) {
                    return 3
                }
            return 1
        }
    }


    fun loadShopSort() {
        NetRepositoryImpl.getShopSort()
            .bindToException()
            .bindToSchedulers()
            .bindToLifecycle(getLifecycleProvider())
            .map {
                val list = mutableListOf<ItemLeftViewModel>()
                it.general_classify.forEach { itemClassify ->
                    list.add(ItemLeftViewModel(this, itemClassify))
                }
                list
            }
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
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .map {
                val list = mutableListOf<Any>()
                it.forEach { itemData ->
                    list.add(ItemTitleViewModel(this, itemData))
                    itemData.info.forEach { itemInfo ->
                        list.add(ItemImageViewModel(this, itemInfo))
                    }
                }
                list
            }
            .subscribe({
                rightList.value = it
                uc.rightRecyclerToTop.call()
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