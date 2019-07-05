package com.zhuzichu.orange

import android.content.Context
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.zhuzichu.mvvm.AppGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.view.plane.PlaneMaker

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-19
 * Time: 14:09
 */

fun checkLogin(context: Context, funcation: () -> Unit) {
    if (!AppGlobal.isLogin.get()) {
        PlaneMaker.showLoadingDialog(context, false)
        AlibcLogin.getInstance().showLogin(object : AlibcLoginCallback {
            override fun onSuccess(i: Int) {
                AppGlobal.isLogin.set(AlibcLogin.getInstance().isLogin)
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