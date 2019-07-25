package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.itemDiff
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 09:32
 */
class FlutterLearnViewModel(application: Application) : BaseViewModel(application) {
    val color= ColorGlobal
    val list =
        DiffObservableList(itemDiff<ItemFlutterLearnViewModel> { oldItem, newItem -> oldItem.title == newItem.title })
            .apply {
                update(
                    listOf(
                        ItemFlutterLearnViewModel(this@FlutterLearnViewModel, 0, "Flutter-Flare")
                    )
                )
            }

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_flutter_learn)


}