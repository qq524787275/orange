package com.zhuzichu.orange.video.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableArrayList
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.Constants
import com.zhuzichu.orange.R
import com.zhuzichu.orange.bean.ShopBean
import com.zhuzichu.orange.repository.NetRepositoryImpl

class VideoViewModel(application: Application) : BaseViewModel(application) {
    private val cid = 1
    private val back = 10
    private var min_id = 1

    val list = ObservableArrayList<Any>()
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_video)

    @SuppressLint("CheckResult")
    fun laodVideoData() {
        NetRepositoryImpl.getVideoList(cid, back, min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map {
                val data = it.data
                val list = mutableListOf<Any>()
                data.forEach { item ->
                    list.add(ItemVideoViewModel(this@VideoViewModel, item.apply {
                        itempic = itempic.plus("_310x310.jpg")
                        videoid = Constants.VIDEO_URL.plus(videoid).plus(".mp4")
                    }))
                }
                list
            }
            .subscribe {
                list.addAll(it)
            }
    }
}