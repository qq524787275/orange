package com.zhuzichu.orange.category.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.BR
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.R
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:42
 * @see R.layout.fragment_category
 */
@SuppressLint("CheckResult")
class CategoryViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val rightRecyclerToTop = SingleLiveEvent<Any>()
    }

    val leftItemBind = itemBindingOf<Any>(BR.item, R.layout.item_category_left)
    val leftList =
        DiffObservableList(itemDiffOf<ItemLeftViewModel> { oldItem, newItem -> oldItem.category.id == newItem.category.id })

    val rightItemBind = OnItemBindClass<Any>().apply {
        map<ItemImageViewModel>(BR.item, R.layout.item_category_right_image)
        map<ItemTitleViewModel>(BR.item, R.layout.item_category_right_title)
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
        NetRepositoryImpl.getCategory(-1L)
            .bindToException()
            .bindToSchedulers()
            .bindToLifecycle(getLifecycleProvider())
            .map {
                getTreeCategory(it.data)
            }
            .subscribe(
                {
                    val data = it.map { item ->
                        ItemLeftViewModel(this, item)
                    }.apply {
                        this[0].isSelected.set(true)
                    }
                    leftList.update(data)
                    updateRight(data[0])
                    showContent()
                },
                {
                    handleThrowable(it)
                    showError()
                }
            )
    }

    private fun getTreeCategory(all: List<CategoryBean>): List<CategoryBean> {
        val list = mutableListOf<CategoryBean>()
        all.forEach {
            if (it.pid == 0L)
                list.add(it)
            all.forEach { item ->
                if (it.id == item.pid) {
                    it.childs.add(item)
                }
            }
        }
        return list
    }

    fun updateRight(itemLeftViewModel: ItemLeftViewModel) {
        val data = mutableListOf<Any>()
        itemLeftViewModel.category.childs.forEach {
            data.add(ItemTitleViewModel(this, it))
            it.childs.forEach { item ->
                data.add(ItemImageViewModel(this, item))
            }
        }
        rightList.value = data
        uc.rightRecyclerToTop.call()
    }
}