package com.zhuzichu.mvvm

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ali.auth.third.core.model.Session

object AppGlobal {

    lateinit var context: Application


    val isLogin = ObservableBoolean(false)
    val session = ObservableField<Session>(Session())

    fun init(context: Application) {
        this.context = context
    }

    fun getAccount(): String {
        return session.get()!!.userid ?: ""
    }
}