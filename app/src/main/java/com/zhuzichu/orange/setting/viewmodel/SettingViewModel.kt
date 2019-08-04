package com.zhuzichu.orange.setting.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.orange.setting.fragment.SettingUserFragment
import com.zhuzichu.orange.utils.checkLogin

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 14:40
 */
class SettingViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val onClickSettingUser = BindingCommand<Any>({
        checkLogin {
            startFragment(SettingUserFragment())
        }
    })
}