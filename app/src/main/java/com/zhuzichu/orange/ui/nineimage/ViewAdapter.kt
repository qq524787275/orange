package com.zhuzichu.orange.ui.nineimage

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhuzichu.mvvm.BR
import com.zhuzichu.orange.R
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.ArrayList

@BindingAdapter("bindNineImages")
fun bindNineImages(recyclerView: RecyclerView, list: List<String>?) {
    if (list == null || list.isEmpty()) {
        return
    }
    val itemBind = ItemBinding.of<ItemNineImageViewModel>(BR.item, R.layout.item_nine_image)
    val items = DiffObservableList(object : DiffUtil.ItemCallback<ItemNineImageViewModel>() {
        override fun areItemsTheSame(oldItem: ItemNineImageViewModel, newItem: ItemNineImageViewModel): Boolean {
            return oldItem.url == newItem.url
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ItemNineImageViewModel, newItem: ItemNineImageViewModel): Boolean {
            return oldItem == newItem
        }
    })
    val data = ArrayList<ItemNineImageViewModel>()
    for (i in list.indices) {
        data.add(
            ItemNineImageViewModel(
                recyclerView,
                ArrayList(list),
                list[i])
        )
    }
    items.update(data)
    BindingRecyclerViewAdapters.setAdapter(recyclerView, itemBind, items, null, null, null, null)
}