package com.zhuzichu.orange.shopdetail.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.bean.ShopDetailBean
import com.zhuzichu.orange.repository.NetRepositoryImpl

@SuppressLint("CheckResult")
class ShopDetailOneViewModel(application: Application) : BaseViewModel(application) {

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onLoadDataCompletedEvent = SingleLiveEvent<ShopDetailBean>()
    }

    fun loadShopDetail(itemid: String) {
        NetRepositoryImpl.getShopDetail(itemid)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                uc.onLoadDataCompletedEvent.value = it.data
            }, {
                handleThrowable(it)
            })
    }
}