package com.zhuzichu.mvvm.view.dialog

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.ali.auth.third.core.util.CommonUtils.toast
import com.trello.rxlifecycle3.LifecycleProvider
import com.zhuzichu.mvvm.base.IBaseViewModel
import com.zhuzichu.mvvm.http.exception.ResponseThrowable

open class BottomSheetViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {
    private lateinit var _lifecycle: LifecycleProvider<*>
    lateinit var _fragment: Fragment

    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>) {
        this._lifecycle = lifecycle
    }

    fun injectFragment(fragment: Fragment) {
        _fragment = fragment
    }

    fun getLifecycleProvider(): LifecycleProvider<*> {
        return _lifecycle
    }

    fun handleThrowable(throwable: Throwable) {
        when (throwable) {
            is ResponseThrowable -> toast(throwable.msg)
        }
        throwable.printStackTrace()
    }
}