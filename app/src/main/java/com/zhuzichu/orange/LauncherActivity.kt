package com.zhuzichu.orange

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.zhuzichu.mvvm.AppGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.main.activity.MainActivity

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 15:31
 */
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initSdk()
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        MainActivity.start(this)
        finish()
    }

    private fun initSdk() {
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(application, object : AlibcTradeInitCallback {
            override fun onSuccess() {
                AppGlobal.isLogin.set(AlibcLogin.getInstance().isLogin)
                AppGlobal.session.set(AlibcLogin.getInstance().session)
                "初始化成功".plus(AlibcLogin.getInstance().isLogin).toast()
            }

            override fun onFailure(code: Int, msg: String) {
                ("初始化失败,错误码=$code / 错误消息=$msg").toast()
            }
        })
    }
}