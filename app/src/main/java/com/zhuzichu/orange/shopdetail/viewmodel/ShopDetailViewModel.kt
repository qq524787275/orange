package com.zhuzichu.orange.shopdetail.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.repository.NetRepositoryImpl

@SuppressLint("CheckResult")
class ShopDetailViewModel(application: Application) : BaseViewModel(application) {
    val text = ObservableField<String>()

    fun loadShopDetail(itemid: String) {
        NetRepositoryImpl.getShopDetail(itemid)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                it.data.toast()
            }, {
                handleThrowable(it)
            })
    }

    fun loadShopDetailDesc(itemid: String, type: String) {
        NetRepositoryImpl.getShopDetailDesc(itemid, type)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .subscribe({
                logi(it)
                it.toast()
                text.set(it)
            }, {
                handleThrowable(it)
            })
    }
}