package com.zhuzichu.orange.ui.picture.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.ui.camerax.viewmodel.ItemAlbumViewModel
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-12
 * Time: 17:01
 */
class PictureViewModel(
    application: Application
) : BaseViewModel(application) {

    val list = AsyncDiffObservableList(itemDiffOf<ItemPictureViewModel> { oldItem, newItem ->
        oldItem.path == newItem.path
    })

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_picture)

    val numInfo = MutableLiveData<String>()

    val onPageSelectedCommand = BindingCommand<Int>(consumer = {
        numInfo.value = it.inc().toString().plus("/").plus(list.size)
    })

    val onClickBack = BindingCommand<Any>({
        back()
    })
}