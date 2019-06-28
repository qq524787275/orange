package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import android.widget.Toast
import com.ali.auth.third.core.model.Session
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.AppGlobal
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.showTradeOrder
import com.zhuzichu.mvvm.utils.showTradeShopCart
import com.zhuzichu.mvvm.utils.toast

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:43
 */
class MineViewModel(application: Application) : BaseViewModel(application) {

    val global = AppGlobal

    val clickLogin = BindingCommand<Any>( {
        AlibcLogin.getInstance().showLogin(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isLogin.set(AlibcLogin.getInstance().isLogin)
                AppGlobal.session.set(AlibcLogin.getInstance().session)
            }

            override fun onFailure(code: Int, msg: String) {
                Toast.makeText(
                    _context, "登录失败 ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    })

    val clickLogout = BindingCommand<Any>( {
        AlibcLogin.getInstance().logout(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isLogin.set(false)
                AppGlobal.session.set(Session())
            }

            override fun onFailure(i: Int, s: String) {
                Toast.makeText(
                    _context, "登出失败 ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    })

    val goSetting = BindingCommand<Any>( {
        "暂未开发".toast()
    })

    val goActivies = BindingCommand<Any>( {
        "暂未开发".toast()
    })

    val goOrder = BindingCommand<Any>( {
        showTradeOrder(_activity)
    })

    val goShopCard = BindingCommand<Any>( {
        showTradeShopCart(_activity)
    })

    val goCollection = BindingCommand<Any>( {
        "暂未开发".toast()
    })
}