package com.zhuzichu.orange.shopdetail.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.utils.checkLogin
import com.zhuzichu.orange.main.fragment.MainFragment
import com.zhuzichu.orange.utils.showTradeDetail
import me.yokeyword.fragmentation.ISupportFragment

@SuppressLint("CheckResult")
class ShopDetailViewModel(
    application: Application
) : BaseViewModel(application) {
    val color = ColorGlobal
    val itemid = ObservableField<String>()
    val itemprice = ObservableField("")
    val itemendprice = ObservableField("")


    val onClickHome = BindingCommand<Any>({
        startFragment(MainFragment(), launchMode = ISupportFragment.SINGLETASK)
    })

    val onClickCollection = BindingCommand<Any>({
        "暂未开发".toast()
    })

    val onClickItemprice = BindingCommand<Any>({
        checkLogin(_activity) {
            itemid.get()?.let { showTradeDetail(_activity, it) }
        }
    })

    val onClickItemendprice = BindingCommand<Any>({
        checkLogin(_activity) {
            itemid.get()?.let {
                showTradeDetail(_activity, it)
            }
        }
    })
}