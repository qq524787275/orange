package com.zhuzichu.mvvm

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ali.auth.third.core.model.Session

object AppGlobal {
    val isLogin = ObservableBoolean(false)
    val session = ObservableField<Session>(Session())

    fun getAccount(): String {
        return session.get()!!.userid ?: ""
    }
}