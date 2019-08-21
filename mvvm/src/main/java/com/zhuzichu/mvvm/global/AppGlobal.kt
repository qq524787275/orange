package com.zhuzichu.mvvm.global

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.ali.auth.third.core.model.Session
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.UploadManager
import com.zhuzichu.mvvm.bean.UserInfoBean
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.global.language.LangGlobal
import com.zhuzichu.mvvm.global.language.Zh


object AppGlobal {

    lateinit var context: Application

    val isLogin = ObservableBoolean(false)
    val userInfo: MutableLiveData<UserInfoBean> = MutableLiveData(UserInfoBean())

    val isAuth = ObservableBoolean(false)
    val session: ObservableField<Session> = ObservableField(Session())

    val ip = ObservableField<String>()

    fun init(context: Application) {
        AppGlobal.context = context
        //初始化颜色
        ColorGlobal.initColor()
        LangGlobal.initLang(Zh())
        CacheGlobal.initDir()
    }

    fun getAccount(): String? {
        return session.get()?.userid
    }

    private val uploadManager = UploadManager(
        Configuration
            .Builder()
            .connectTimeout(10)           // 链接超时。默认10秒
            .responseTimeout(60)          // 服务器响应超时。默认60秒
            .build()
    )

    fun getUploadManager(): UploadManager {
        return uploadManager
    }
}