package com.zhuzichu.orange.setting.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bean.UserInfoBean
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.bindToException
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.bindToSchedulers
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.Constants
import com.zhuzichu.orange.R
import com.zhuzichu.orange.setting.fragment.AddressDialogFragment
import com.zhuzichu.orange.setting.fragment.EditAvatarFragment
import com.zhuzichu.orange.setting.fragment.EditItemFragment
import com.zhuzichu.orange.setting.fragment.SelectItemFragment

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

    val onClickAvatar = BindingCommand<Any>({
        startFragment(EditAvatarFragment())
    })

    val onClickSex = BindingCommand<Any>({
        val selectItemFragment = SelectItemFragment(_fragment.childFragmentManager, consumer = {
            val array = _context.resources.getStringArray(R.array.list_sex)
            array.mapIndexed { index, item ->
                ItemSelectViewModel(it, item, index, userInfo.get()?.sex == index)
            }
        }) {
            updateUserInfo(Constants.TYPE_SEX, it.value)
        }

        selectItemFragment.show()
    })

    val onClickNickname = BindingCommand<Any>({
        val editItemFragment = EditItemFragment("编辑昵称", userInfo.get()?.nickname ?: "", "15位以内字符组成", 15) { s, f ->
            s?.let {
                updateUserInfo(Constants.TYPE_NICKNAME, it, f)
            }
        }
        startFragment(editItemFragment)
    })

    val onClickEmail = BindingCommand<Any>({
        val editItemFragment = EditItemFragment("编辑邮箱", userInfo.get()?.email ?: "", "请输入有效的邮箱", 100) { s, f ->
            s?.let {
                updateUserInfo(Constants.TYPE_EMAIL, it, f)
            }
        }
        startFragment(editItemFragment)
    })


    val onClickSummary = BindingCommand<Any>({
        val editItemFragment = EditItemFragment("编辑个人简介", userInfo.get()?.summary ?: "", "请输入个人简介", 100) { s, f ->
            s?.let {
                updateUserInfo(Constants.TYPE_SUMMARY, it, f)
            }
        }
        startFragment(editItemFragment)
    })


    val onClickLocation = BindingCommand<Any>({
        val addressFragment = AddressDialogFragment(_fragment.childFragmentManager) {
            val sb = StringBuilder()
            it.forEach { item ->
                sb.append(item?.name)
            }
            val value = sb.toString()
            updateUserInfo(Constants.TYPE_LOCATION, value)
        }
        addressFragment.show()
    })

    @SuppressLint("CheckResult")
    fun updateUserInfo(type: Int, value: Any, fragment: BaseFragment<*, *>? = null) {
        NetRepositoryImpl.updateUserInfo(type, value)
            .bindToException()
            .bindToLifecycle(getLifecycleProvider())
            .bindToSchedulers()
            .doOnSubscribe { showLoadingDialog() }
            .doFinally { hideLoadingDialog() }
            .subscribe(
                {
                    val data = it.data
                    userInfo.set(data.apply {
                        avatarUrl=Constants.APP_IMAGE_URL.plus(avatarUrl)
                    })
                    global.userInfo.set(data.apply {
                        avatarUrl=Constants.APP_IMAGE_URL.plus(avatarUrl)
                    })
                    fragment?.pop()
                },
                {
                    handleThrowable(it)
                }
            )
    }
}