package com.zhuzichu.orange.login.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-26
 * Time: 09:41
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {

    val username = ObservableField<String>()
    val password = ObservableField<String>()
}