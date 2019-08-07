package com.zhuzichu.orange.login.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.AppPreference
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.login.fragment.LoginFragment
import com.zhuzichu.orange.main.fragment.MainFragment
import me.yokeyword.fragmentation.ISupportFragment

class RegistViewModel(application: Application) : BaseViewModel(application) {
    val preference by lazy { AppPreference() }
    val username = ObservableField<String>("18229858146")
    val password = ObservableField<String>("18229858146")
    val confirmPassword = ObservableField<String>("18229858146")
    val phone = ObservableField<String>("18229858146")
    val code = ObservableField<String>("45678")

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onClickCodeEvent = SingleLiveEvent<Any>()
    }

    val onClickCode = BindingCommand<Any>({
        getRegistCode()
    })

    val onClickRegist = BindingCommand<Any>({
        loadRegist(false)
    })

    val onClickRegistAndLogin = BindingCommand<Any>({
        loadRegist(true)
    })


    @SuppressLint("CheckResult")
    private fun loadRegist(isLogin: Boolean) {
        val username = username.get()
        val password = password.get()
        val confirmPassword = confirmPassword.get()
        val phone = phone.get()
        val code = code.get()
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
            if (confirmPassword.isNullOrBlank()) {
                "确认密码不能为空".toast()
                break
            }
            if (password != confirmPassword) {
                "两次输入的密码不一致".toast()
                break
            }
            if (phone.isNullOrBlank()) {
                "手机号不能为空".toast()
                break
            }
            if (!isMobileExact(phone)) {
                "请输入一个有效的手机号".toast()
                break
            }
            if (code.isNullOrBlank()) {
                "验证码不能为空".toast()
                break
            }
            NetRepositoryImpl.regist(username, password, phone, code)
                .bindToSchedulers()
                .bindToException()
                .bindToLifecycle(getLifecycleProvider())
                .doOnSubscribe {
                    showLoadingDialog()
                }
                .doFinally {
                    hideLoadingDialog()
                }
                .subscribe(
                    {
                        preference.token = it.data.token
                        if (isLogin) {
                            "登录成功~".toast()
                            AppGlobal.isLogin.set(true)
                            AppGlobal.userInfo.value=it.data.userInfo
                            startFragment(MainFragment(), launchMode = ISupportFragment.SINGLETASK)
                        } else {
                            "注册成功~".toast()
                            startFragment(LoginFragment(), launchMode = ISupportFragment.SINGLETASK)
                        }
                    },
                    {
                        handleThrowable(it)
                    }
                )

        } while (false)

    }

    @SuppressLint("CheckResult")
    private fun getRegistCode() {
        val phone = phone.get()
        do {
            if (phone.isNullOrBlank()) {
                "手机号不能为空".toast()
                break
            }
            if (!isMobileExact(phone)) {
                "请输入一个有效的手机号".toast()
                break
            }
            NetRepositoryImpl.getRegistCode(phone)
                .bindToLifecycle(getLifecycleProvider())
                .bindToException()
                .bindToSchedulers()
                .doOnSubscribe {
                    showLoadingDialog()
                }
                .doFinally {
                    hideLoadingDialog()
                }
                .subscribe(
                    {
                        uc.onClickCodeEvent.call()
                    },
                    {
                        handleThrowable(it)
                    }
                )
        } while (false)
    }
}