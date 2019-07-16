package com.zhuzichu.orange.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.bean.TalentcatBean
import com.zhuzichu.orange.repository.NetRepositoryImpl

class TalentViewModel(application: Application) : BaseViewModel(application) {
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onLoadDataSuccess = SingleLiveEvent<List<TalentcatBean.Topdata>>()
    }


    @SuppressLint("CheckResult")
    fun loadData() {
        NetRepositoryImpl.getTalentcat()
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val topdata = it.data.topdata
                uc.onLoadDataSuccess.value = topdata
            }, {
                handleThrowable(it)
            })
    }


}