package com.zhuzichu.orange.setting.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.afollestad.materialdialogs.MaterialDialog
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.bindToException
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.bindToSchedulers
import com.zhuzichu.mvvm.utils.getVersionName
import com.zhuzichu.orange.main.fragment.UpdateDialog
import com.zhuzichu.orange.main.fragment.UpdateFragment
import com.zhuzichu.orange.setting.fragment.SettingUserFragment
import com.zhuzichu.orange.utils.checkLogin

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 14:40
 */
class SettingViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val onClickSettingUser = BindingCommand<Any>({
        checkLogin {
            startFragment(SettingUserFragment())
        }
    })

    val onClickUpdate = BindingCommand<Any>({
        checkUpdate()
    })

    val versionName = MutableLiveData<String>().apply {
        value = "V".plus(getVersionName())
    }

    @SuppressLint("CheckResult")
    fun checkUpdate() {
        NetRepositoryImpl.checkUpdate()
            .bindToSchedulers()
            .bindToLifecycle(getLifecycleProvider())
            .bindToException()
            .subscribe(
                { version ->
                    if (version.data.isUpdate) {
                        UpdateDialog(_activity,version.data.info).show()
                    }
                },
                {
                    handleThrowable(it)
                }
            )
    }
}