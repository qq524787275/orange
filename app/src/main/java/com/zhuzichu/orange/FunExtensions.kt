package com.zhuzichu.orange

import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.AppGlobal
import com.zhuzichu.mvvm.utils.toast

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-19
 * Time: 14:09
 */

fun checkLogin(funcation: () -> Unit) {
    if (!AppGlobal.isLogin.get()) {
        AlibcLogin.getInstance().showLogin(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isLogin.set(AlibcLogin.getInstance().isLogin)
                AppGlobal.session.set(AlibcLogin.getInstance().session)
                funcation.invoke()
            }

            override fun onFailure(code: Int, msg: String) {
                     msg.toast()

            }
        })
        return
    }
    funcation.invoke()
}