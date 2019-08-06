package com.zhuzichu.orange.setting.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseSheetViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-06
 * Time: 14:11
 */
class SelectItemViewModel(application: Application) : BaseSheetViewModel(application) {

    val color = ColorGlobal

    val list = MutableLiveData<List<Any>>()

    val text = MutableLiveData<String>()

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_select_item)

    val onSelectItemEvent = SingleLiveEvent<ItemSelectViewModel>()
}