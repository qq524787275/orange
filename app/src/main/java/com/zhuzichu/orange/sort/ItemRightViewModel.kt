package com.zhuzichu.orange.sort

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.bean.SortBean
import io.reactivex.Flowable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 11:26
 */
class SortItemRightViewModel(viewModel: SortViewModel, var data: SortBean.Data) :
    ItemViewModel<SortViewModel>(viewModel) {

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_sort_right_image)
    private val liveData = MutableLiveData<List<ItemImageViewModel>>().apply {
        Flowable.fromArray(data.info)
            .compose(bindToLifecycle(viewModel.getLifecycleProvider()))
            .compose(schedulersTransformer())
            .flatMap {
                Flowable.fromIterable(it)
            }
            .map {
                ItemImageViewModel(viewModel,it)
            }
            .toList()
            .subscribe({
                value=it
            },{
                viewModel.handleThrowable(it)
            })

    }
    val list: LiveData<List<Any>> = Transformations.map(liveData) { it }
    val diff: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemImageViewModel && newItem is ItemImageViewModel) {
                oldItem.info.imgurl == newItem.info.imgurl
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

    }
}

