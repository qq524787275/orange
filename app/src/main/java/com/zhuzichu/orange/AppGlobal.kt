package com.zhuzichu.orange

import androidx.lifecycle.MutableLiveData
import com.ali.auth.third.core.model.Session

object AppGlobal {
    val sessionLiveData= MutableLiveData<Session>()

    fun getAccount(): String {
        return ""
    }


}