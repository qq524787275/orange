package com.zhuzichu.orange.video.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.http.exception.ExceptionHandle
import com.zhuzichu.mvvm.http.exception.ResponseThrowable
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.Constants
import com.zhuzichu.orange.R
import com.zhuzichu.orange.repository.NetRepositoryImpl

class VideoViewModel(application: Application) : BaseViewModel(application) {
    private val cid = 1
    private val back = 10
    private var min_id = 1
    val viewState = ObservableInt(MultiStateView.VIEW_STATE_LOADING)
    //封装一个界面发生改变的观察者
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        //下拉刷新完成
        val finishRefreshing = SingleLiveEvent<Any>()
        //上拉加载完成
        val finishLoadmore = SingleLiveEvent<Any>()
        //上拉加载完成 并且到最后一数据
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val list = ObservableArrayList<Any>()
    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_video)

    val onRefreshCommand = BindingCommand<Any>( {
        min_id = 1
        laodVideoData()
    })

    val onLoadMoreCommand = BindingCommand<Any>( {
        laodVideoData()
    })

    @SuppressLint("CheckResult")
    fun laodVideoData() {
        NetRepositoryImpl.getVideoList(cid, back, min_id)
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe({
                val data = it.data
                val list = mutableListOf<Any>()
                data.forEach { item ->
                    list.add(ItemVideoViewModel(this@VideoViewModel, item.apply {
                        itempic = itempic.plus("_310x310.jpg")
                        videoid = Constants.VIDEO_URL.plus(videoid).plus(".mp4")
                    }))
                }
                if (min_id == 1) {
                    this@VideoViewModel.list.clear()
                    uc.finishRefreshing.call()
                }
                this@VideoViewModel.list.addAll(list)
                if (data.size < back) {
                    uc.finishLoadMoreWithNoMoreData.call()
                } else {
                    uc.finishLoadmore.call()
                }
                min_id = it.min_id
                viewState.set(MultiStateView.VIEW_STATE_CONTENT)
            }, {
                if (it is ResponseThrowable) {
                    if (it.code == ExceptionHandle.ERROR.NO_DATA && list.size == 0) {
                        viewState.set(MultiStateView.VIEW_STATE_EMPTY)
                    } else {
                        viewState.set(MultiStateView.VIEW_STATE_CONTENT)
                    }
                }
                handleThrowable(it)
                uc.finishLoadmore.call()
            })
    }
}