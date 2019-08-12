package com.zhuzichu.orange.ui.camerax.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.ui.camerax.CameraActivity
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

class AlbumViewModel(application: Application) : BaseViewModel(application) {
    val list = AsyncDiffObservableList(itemDiffOf<ItemAlbumViewModel> { oldItem, newItem -> oldItem == newItem })

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_album)

    private val current = MutableLiveData(0)

    val onPageSelectedCommand = BindingCommand<Int>(consumer = {
        current.value = it
    })

    val onClickChoose = BindingCommand<Any>({
        val current = current.value
        if (list.isNullOrEmpty() || current == null) {
            "当前没有可选择的图片".toast()
            return@BindingCommand
        }
        val result = Intent()
        result.putExtra(CameraActivity.EXTRA_PATH, (list[current] as ItemAlbumViewModel).resource.path)
        _activity.setResult(Activity.RESULT_OK, result)
        _activity.finish()
    })

    val onClickDelete = BindingCommand<Any>({

    })

    val onClickBack = BindingCommand<Any>({
        back()
    })
}