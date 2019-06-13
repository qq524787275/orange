package com.zhuzichu.mvvm.base

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.trello.rxlifecycle3.LifecycleProvider
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.http.exception.ResponseThrowable
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.mvvm.view.layout.MultiStateView
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 15:16
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {
    val context: Context = application.applicationContext
    private val uc: UIChangeLiveData = UIChangeLiveData()
    private lateinit var lifecycle: LifecycleProvider<*>
    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = lifecycle
    }

    fun getLifecycleProvider(): LifecycleProvider<*> {
        return lifecycle
    }

    fun getUC(): UIChangeLiveData {
        return uc
    }

    fun showLoadingDialog() {
        uc.getShowLoadingDialogEvent().call()
    }

    fun hideLoadingDialog() {
        uc.getHideLoadingDialogEvent().call()
    }

    fun showContent() {
        uc.getMultiStateEvent().postValue(MultiStateView.VIEW_STATE_CONTENT)
    }

    fun showError() {
        uc.getMultiStateEvent().postValue(MultiStateView.VIEW_STATE_ERROR)
    }

    fun showEmpty() {
        uc.getMultiStateEvent().postValue(MultiStateView.VIEW_STATE_EMPTY)
    }

    fun showLoading() {
        uc.getMultiStateEvent().postValue(MultiStateView.VIEW_STATE_LOADING)
    }

    fun handleThrowable(throwable: Throwable) {
        when (throwable) {
            is ResponseThrowable -> toast(throwable.msg)
        }
        throwable.printStackTrace()
    }

    fun startFragment(fragment: Fragment, bundle: Bundle? = null) {
        val params = HashMap<String, Any>()
        if (bundle != null) {
            fragment.arguments = bundle
        }
        params[ParameterField.FRAGMENT] = fragment
        uc.getStartFragmentEvent().postValue(params)
    }

    fun startActivity(clz: Class<*>, bundle: Bundle? = null, isPop: Boolean? = false, options: Bundle? = null) {
        val params = HashMap<String, Any>()
        params[ParameterField.CLASS] = clz
        bundle?.let { params[ParameterField.BUNDLE] = it }
        options?.let { params[ParameterField.OPTIONS] = it }
        isPop?.let { params[ParameterField.POP] = it }
        uc.getStartActivityEvent().postValue(params)
    }

    inner class UIChangeLiveData : SingleLiveEvent<Any>() {
        private val startActivityEvent: SingleLiveEvent<Map<String, Any>> = SingleLiveEvent()
        private val startFragmentEvent: SingleLiveEvent<Map<String, Any>> = SingleLiveEvent()
        private val finishEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        private val onBackPressedEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        private val multiStateEvent: SingleLiveEvent<Int> = SingleLiveEvent()
        private val onShowLoadingDialog: SingleLiveEvent<Void> = SingleLiveEvent()
        private val onHideLoadingDialog: SingleLiveEvent<Void> = SingleLiveEvent()

        fun getHideLoadingDialogEvent(): SingleLiveEvent<Void> {
            return onHideLoadingDialog
        }

        fun getShowLoadingDialogEvent(): SingleLiveEvent<Void> {
            return onShowLoadingDialog
        }

        fun getMultiStateEvent(): SingleLiveEvent<Int> {
            return multiStateEvent
        }

        fun getStartActivityEvent(): SingleLiveEvent<Map<String, Any>> {
            return startActivityEvent
        }

        fun getStartFragmentEvent(): SingleLiveEvent<Map<String, Any>> {
            return startFragmentEvent
        }

        fun getFinishEvent(): SingleLiveEvent<Void> {
            return finishEvent
        }

        fun getOnBackPressedEvent(): SingleLiveEvent<Void> {
            return onBackPressedEvent
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in Any>) {
            super.observe(owner, observer)
        }
    }

    object ParameterField {
        var FRAGMENT = "FRAGMENT"
        var CLASS = "CLASS"
        var BUNDLE = "BUNDLE"
        var POP = "POP"
        var OPTIONS = "OPTIONS"
    }

}