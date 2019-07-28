package com.zhuzichu.orange.login.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.repository.NetRepositoryImpl

class RegistViewModel(application: Application) : BaseViewModel(application) {

    val username = ObservableField<String>("18229858146")
    val password = ObservableField<String>("18229858146")
    val confirmPassword = ObservableField<String>("18229858146")
    val phone = ObservableField<String>("18229858146")
    val code = ObservableField<String>("45678")

    val onClickRegist = BindingCommand<Any>({
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
            loadRegist(username, password, phone, code)
        } while (false)
    })

    @SuppressLint("CheckResult")
    private fun loadRegist(
        username: String,
        password: String,
        phone: String,
        code: String
    ) {
        NetRepositoryImpl.regist(username, password, phone, code)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe(
                {
                    it.data.token.toast()
                },
                {
                    handleThrowable(it)
                }
            )
    }
}