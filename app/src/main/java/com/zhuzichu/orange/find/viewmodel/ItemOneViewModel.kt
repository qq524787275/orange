package com.zhuzichu.orange.find.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.bean.SelectedItemBean
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 11:40
 */
class ItemOneViewModel(
    viewModel: FindOneViewModel,
    var selectedItemBean: SelectedItemBean
) : ItemViewModel<FindOneViewModel>(viewModel) {
    val color = ColorGlobal
    val list = MutableLiveData<List<Any>>().apply {
        value = selectedItemBean.itempic.mapIndexed{index,item->
            ItemOneImageViewModel(viewModel, item.plus("_310x310.jpg"),index,selectedItemBean.itempic)
        }
    }
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_one_image)
    val onClickCopy = BindingCommand<Any>({
        //获取剪贴板管理器：
        val cm = ContextCompat.getSystemService(viewModel._context, ClipboardManager::class.java)
// 创建普通字符型ClipData
        val clipData = ClipData.newPlainText("Label", selectedItemBean.copy_comment)
// 将ClipData内容放到系统剪贴板里。
        cm?.setPrimaryClip(clipData)
        "复制成功～".toast()
    })
}