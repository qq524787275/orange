package com.zhuzichu.orange.sort

import android.annotation.SuppressLint
import android.app.Application
import com.secretk.move.RepositoryImpl
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.mvvm.utils.toast

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:42
 */
@SuppressLint("CheckResult")
class SortViewModel(application: Application) : BaseViewModel(application) {

    override fun onFirstUserVisible() {
        loadShopList()
    }

    private fun loadShopList() {
        RepositoryImpl.getShopList(3, 0, 10, 1)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                it.data.size.toast()
            }, {})
    }
}