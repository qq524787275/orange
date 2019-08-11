package com.zhuzichu.orange.camerax.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

class AlbumViewModel(application: Application) : BaseViewModel(application) {
    val list = AsyncDiffObservableList(itemDiffOf<ItemAlbumViewModel> { oldItem, newItem -> oldItem == newItem })

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_album)

    val onClickChoose = BindingCommand<Any>({

    })

    val onClickDelete = BindingCommand<Any>({

    })

    val onClickBack = BindingCommand<Any>({
        back()
    })
}