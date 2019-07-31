package com.zhuzichu.orange.login.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.AppPreference
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.login.fragment.ForgetFragment
import com.zhuzichu.orange.login.fragment.RegistFragment
import com.zhuzichu.orange.main.fragment.MainFragment
import com.zhuzichu.orange.repository.NetRepositoryImpl
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-26
 * Time: 09:41
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {
    val preference by lazy { AppPreference() }
    val username = ObservableField<String>("18229858146")
    val password = ObservableField<String>("18229858146")
    val onClickRegist = BindingCommand<Any>({
        startFragment(RegistFragment())
    })

    val onClickForget = BindingCommand<Any>({
        startFragment(ForgetFragment())
    })

    val onClickLogin = BindingCommand<Any>({
        val username = username.get()
        val password = password.get()
        do {
            if (username.isNullOrBlank()) {
                "账号不能为空".toast()
                break
            }
            if (!isUsername(username)) {
                "用户名必须是6-20位，不能以\"_\"结尾".toast()
                break
            }
            if (password.isNullOrBlank()) {
                "密码不能为空".toast()
                break
            }
            NetRepositoryImpl.login(username, md5(password))
                .bindToSchedulers()
                .bindToLifecycle(getLifecycleProvider())
                .bindToException()
                .doOnSubscribe { showLoadingDialog() }
                .doFinally { hideLoadingDialog() }
                .subscribe(
                    {
                        val token = it.data.token
                        preference.token = token
                        AppGlobal.isLogin.set(true)
                        AppGlobal.userInfo.set(it.data.userInfo)
                        "登录成功～".toast()
                        startFragment(MainFragment(), launchMode = ISupportFragment.SINGLETASK)
                    },
                    {
                        handleThrowable(it)
                    }
                )
        } while (false)
    })
}