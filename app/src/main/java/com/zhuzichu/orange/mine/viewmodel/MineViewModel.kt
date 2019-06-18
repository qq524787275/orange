package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import android.widget.Toast
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:43
 */
class MineViewModel(application: Application) : BaseViewModel(application) {
    val clickLogin = BindingCommand<Any>(BindingAction {

        val alibcLogin = AlibcLogin.getInstance()
        alibcLogin.showLogin(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                Toast.makeText(
                    _context, "登录成功 ",
                    Toast.LENGTH_LONG
                ).show()

                AlibcLogin.getInstance().session
            }

            override fun onFailure(code: Int, msg: String) {
                Toast.makeText(
                    _context, "登录失败 ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    })

    val clickLogout = BindingCommand<Any>(BindingAction {
        val alibcLogin = AlibcLogin.getInstance()
        alibcLogin.logout(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                Toast.makeText(
                    _context, "登出成功 ",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onFailure(i: Int, s: String) {
                Toast.makeText(
                    _context, "登录失败 ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    })

}