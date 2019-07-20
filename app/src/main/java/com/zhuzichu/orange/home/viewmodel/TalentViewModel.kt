package com.zhuzichu.orange.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.itemDiff
import com.zhuzichu.orange.repository.NetRepositoryImpl
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

class TalentViewModel(application: Application) : BaseViewModel(application) {

    val list =
        AsyncDiffObservableList(itemDiff<ItemTalentBannerViewModel> { oldItem, newItem ->
            oldItem.data.id == newItem.data.id
        })

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_talent_banner)

    @SuppressLint("CheckResult")
    fun loadData() {
        NetRepositoryImpl.getTalentcat()
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val topdata = it.data.topdata
                list.update(topdata.map { item ->
                    ItemTalentBannerViewModel(this@TalentViewModel, item)
                })
            }, {
                handleThrowable(it)
            })
    }


}