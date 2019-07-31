package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 15:40
 */
class SettingUserViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val global = AppGlobal

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onShowLogoutSnackbarEvent = SingleLiveEvent<Any>()
    }

    val onClickLogout = BindingCommand<Any>({
        uc.onShowLogoutSnackbarEvent.call()
    })
}