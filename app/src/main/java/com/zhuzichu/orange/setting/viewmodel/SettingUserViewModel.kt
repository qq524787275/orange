package com.zhuzichu.orange.setting.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bean.UserInfoBean
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.setting.fragment.AddressDialogFragment
import com.zhuzichu.orange.setting.fragment.EditItemFragment

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
    val userInfo = ObservableField<UserInfoBean>(global.userInfo.get())

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onShowLogoutSnackbarEvent = SingleLiveEvent<Any>()
    }

    val onClickLogout = BindingCommand<Any>({
        uc.onShowLogoutSnackbarEvent.call()
    })

    val onClickNickname = BindingCommand<Any>({
        val editItemFragment = EditItemFragment("编辑昵称", userInfo.get()?.nickname ?: "", "15位以内字符组成", 15) {
            it?.toast()
        }
        startFragment(editItemFragment)
    })

    val onClickEmail = BindingCommand<Any>({
        val editItemFragment = EditItemFragment("编辑邮箱", userInfo.get()?.email ?: "", "请输入有效的邮箱", 100) {
            it?.toast()
        }
        startFragment(editItemFragment)
    })


    val onClickSummary = BindingCommand<Any>({
        val editItemFragment = EditItemFragment("编辑个人简介", userInfo.get()?.summary ?: "", "请输入个人简介", 100) {
            it?.toast()
        }
        startFragment(editItemFragment)
    })


    val onClickLocation = BindingCommand<Any>({
        val addressFragment = AddressDialogFragment(_fragment.childFragmentManager) {
            val sb = StringBuilder()
            it.forEach { item ->
                sb.append(item?.name)
            }
            sb.toString().toast()
        }
        addressFragment.show()
    })
}