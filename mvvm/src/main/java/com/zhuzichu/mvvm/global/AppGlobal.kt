package com.zhuzichu.mvvm.global

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ali.auth.third.core.model.Session
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.global.language.LangGlobal
import com.zhuzichu.mvvm.global.language.Zh

object AppGlobal {

    lateinit var context: Application


    val isLogin = ObservableBoolean(false)
    val session = ObservableField(Session())

    fun init(context: Application) {
        AppGlobal.context = context
        //初始化颜色
        ColorGlobal.initColor()
        LangGlobal.initLang(Zh())
    }

    fun getAccount(): String {
        return session.get()!!.userid ?: ""
    }
}