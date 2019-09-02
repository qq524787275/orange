package com.zhuzichu.orange.utils

import android.content.Context
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.login.fragment.LoginFragment
import com.zhuzichu.orange.view.plane.PlaneMaker
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-19
 * Time: 14:09
 */

fun checkAuth(context: Context, funcation: () -> Unit) {
    if (!AppGlobal.isAuth.get()) {
        PlaneMaker.showLoadingDialog(context, false)
        AlibcLogin.getInstance().showLogin(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isAuth.set(AlibcLogin.getInstance().isLogin)
                AppGlobal.session.set(AlibcLogin.getInstance().session)
                funcation.invoke()
                PlaneMaker.dismissLodingDialog()
            }

            override fun onFailure(code: Int, msg: String) {
                msg.toast()
                PlaneMaker.dismissLodingDialog()
            }
        })
        return
    }
    funcation.invoke()
}


fun BaseViewModel?.checkLogin(login: () -> Unit) {
    if (!AppGlobal.isLogin.get()) {
        this?.startFragment(LoginFragment(), launchMode = ISupportFragment.SINGLETASK)
        return
    }
    login.invoke()
}

fun Boolean.isTrue(funcation: () -> Unit) {
    if (this) {
        funcation.invoke()
    }
}

fun Boolean.isFalse(funcation: () -> Unit) {
    if (!this) {
        funcation.invoke()
    }
}
