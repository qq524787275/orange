package com.zhuzichu.orange.mine.viewmodel

import android.app.Application
import com.ali.auth.third.core.model.Session
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.AppGlobal
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
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
        AlibcLogin.getInstance().logout(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isLogin.set(false)
                AppGlobal.session.set(Session())
            }

            override fun onFailure(code: Int, msg: String) {
                msg.toast()
            }
        })
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
}