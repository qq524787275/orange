package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 09:32
 */
class SmallProgramViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val list =
        DiffObservableList(itemDiffOf<ItemSmallProgramViewModel> { oldItem, newItem -> oldItem.title == newItem.title })
            .apply {
                update(
                    listOf(
                        ItemSmallProgramViewModel(this@SmallProgramViewModel, 0, "崩溃测试"),
                        ItemSmallProgramViewModel(this@SmallProgramViewModel, 1, "Flutter-Flare"),
                        ItemSmallProgramViewModel(this@SmallProgramViewModel, 2, "AddressSelector"),
                        ItemSmallProgramViewModel(this@SmallProgramViewModel, 3, "Camerax"),
                        ItemSmallProgramViewModel(this@SmallProgramViewModel, 4, "Ball-Game")
                    )
                )
            }

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_flutter_learn)


}