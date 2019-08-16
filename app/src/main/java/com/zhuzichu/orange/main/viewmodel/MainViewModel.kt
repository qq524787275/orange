package com.zhuzichu.orange.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.MaterialDialog
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.bindToException
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.bindToSchedulers
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.main.fragment.UpdateDialog
import com.zhuzichu.orange.main.fragment.UpdateFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 15:21
 */
class MainViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal


    @SuppressLint("CheckResult")
    fun checkUpdate() {
        NetRepositoryImpl.checkUpdate()
            .bindToSchedulers()
            .bindToLifecycle(getLifecycleProvider())
            .bindToException()
            .subscribe(
                { version ->
                    if (version.data.isUpdate) {
                        UpdateDialog(_activity).show()
//                        MaterialDialog(_activity).show {
//                            title(
//                                text = "更新提示"
//                            )
//                            message(
//                                text = "亲，有新版本了，是否需要下载?"
//                            )
//                            positiveButton(
//                                text = "去下载"
//                            ) {
//                                startFragment(
//                                    UpdateFragment(), bundle = bundleOf(
//                                        UpdateFragment.VERSION_INFO to version.data.info
//                                    )
//                                )
//                            }
//                            negativeButton(
//                                text = "取消"
//                            )
//                        }
                    }
                },
                {
                    handleThrowable(it)
                }
            )
    }
}