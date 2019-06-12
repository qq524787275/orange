package com.zhuzichu.orange.sort

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import com.secretk.move.RepositoryImpl
import com.zhuzichu.mvvm.BR
import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.R
import com.zhuzichu.orange.bean.SortBean
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.intellij.lang.annotations.Flow

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:42
 */
@SuppressLint("CheckResult")
class SortViewModel(application: Application) : BaseViewModel(application) {

    var current = 0

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_sort_left)

    private val mutableLiveData = MutableLiveData<List<SortItemViewModel>>().apply { value = ArrayList() }

    val list: LiveData<List<Any>> = Transformations.map<List<SortItemViewModel>, List<Any>>(mutableLiveData) {
        it
    }

    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is SortItemViewModel && newItem is SortItemViewModel) {
                oldItem.sortBean.cid == newItem.sortBean.cid
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }

    override fun onFirstUserVisible() {
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
                SortItemViewModel(this, it)
            }
            .toList()
            .subscribe({
                it[current].isSelected.set(true)
                mutableLiveData.value = it
            }, {
                handleThrowable(it)
            })
    }

    fun selectItem(sortBean: SortItemViewModel) {
        sortBean.isSelected.set(true)
        mutableLiveData.value?.get(current)?.isSelected?.set(false)
        current = mutableLiveData.value?.indexOf(sortBean)!!
    }
}