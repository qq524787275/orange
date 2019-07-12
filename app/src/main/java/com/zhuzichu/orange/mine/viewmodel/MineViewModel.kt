package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import androidx.appcompat.widget.AppCompatCheckBox
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.mine.fragment.CacheFragment
import com.zhuzichu.orange.utils.showTradeOrder
import com.zhuzichu.orange.utils.showTradeShopCart
import com.zhuzichu.orange.view.plane.PlaneMaker

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:43
 */
class MineViewModel(application: Application) : BaseViewModel(application) {
    val global = AppGlobal
    val color = ColorGlobal
    lateinit var checkbox: AppCompatCheckBox

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onDarkChangeEvent = SingleLiveEvent<Boolean>()

        val onShowLogoutSnackbarEvent = SingleLiveEvent<Any>()
    }


    val onClickLogin = BindingCommand<Any>({
        PlaneMaker.showLoadingDialog(_activity, false)
        AlibcLogin.getInstance().showLogin(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isLogin.set(AlibcLogin.getInstance().isLogin)
                AppGlobal.session.set(AlibcLogin.getInstance().session)
                PlaneMaker.dismissLodingDialog()
            }

            override fun onFailure(code: Int, msg: String) {
                msg.toast()
                PlaneMaker.dismissLodingDialog()
            }
        })
    })

    val onClickLogout = BindingCommand<Any>({
        uc.onShowLogoutSnackbarEvent.call()
    })

    val getDarkCheckBox = BindingCommand<AppCompatCheckBox>(consumer = {
        checkbox = it
    })

    val onClickOrderAll = BindingCommand<Any>({
        showTradeOrder(_activity, 0)
    })

    val onClickOrderOne = BindingCommand<Any>({
        showTradeOrder(_activity, 1)
    })

    val onClickOrderTwo = BindingCommand<Any>({
        showTradeOrder(_activity, 2)
    })

    val onClickOrderThree = BindingCommand<Any>({
        showTradeOrder(_activity, 3)
    })

    val onClickOrderFour = BindingCommand<Any>({
        showTradeOrder(_activity, 4)
    })

    val onClickSetting = BindingCommand<Any>({
        "暂未开发".toast()
    })

    val onClickActivies = BindingCommand<Any>({
        "暂未开发".toast()
    })

    val onClickOrder = BindingCommand<Any>({
        showTradeOrder(_activity)
    })

    val onClickShopcar = BindingCommand<Any>({
        showTradeShopCart(_activity)
    })

    val onClickCollection = BindingCommand<Any>({
        "暂未开发".toast()
    })

    val onClickCache = BindingCommand<Any>({
        startFragment(CacheFragment())
    })

    val onClickTheme = BindingCommand<Any>({
        MaterialDialog(_activity).show {
            title(text = "选择主题颜色")
            positiveButton(text = "确定")
            cornerRadius(10f)
            colorChooser(
                ColorPalette.Accent,
                ColorPalette.AccentSub
            ) { _, color ->
                this@MineViewModel.color.colorPrimary.value = color
            }
        }
    })

    val onClickDark = BindingCommand<Any>({
        uc.onDarkChangeEvent.value = !color.isDark.value!!
    })
}