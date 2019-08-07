package com.zhuzichu.mvvm.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import com.zhuzichu.mvvm.utils.cast
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.mvvm.view.dialog.BottomSheetDialog
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.lang.reflect.ParameterizedType
import kotlin.math.log

abstract class BaseSheetFragment<V : ViewDataBinding, VM : BaseSheetViewModel>(
    val fm: FragmentManager
) : DialogFragment(), LifecycleProvider<FragmentEvent>, IBaseFragment {
    private val lifecycleSubject: BehaviorSubject<FragmentEvent> =
        BehaviorSubject.create()
    lateinit var _bind: V
    lateinit var _viewModel: VM
    lateinit var _contentView: View

    abstract fun setLayoutId(): Int
    abstract fun bindVariableId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = this.javaClass.genericSuperclass
        if (type is ParameterizedType) _viewModel =
            ViewModelProviders.of(this).get(cast(type.actualTypeArguments[1]))
        lifecycle.addObserver(_viewModel)
        _viewModel.injectLifecycleProvider(this)
        _viewModel.injectFragment(this)
        _contentView = inflater.inflate(setLayoutId(), container, false)
        _bind = DataBindingUtil.bind(_contentView)!!
        _contentView.postDelayed({
            onEnterAnimationEnd(savedInstanceState)
        }, 200)
        _bind.setVariable(bindVariableId(), _viewModel)
        return _bind.root.also {
            _bind.lifecycleOwner = this
            _bind.executePendingBindings()
        }
    }

    open fun onEnterAnimationEnd(savedInstanceState: Bundle?) {}


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        "onCreateDialog".logi("zzc")
        return BottomSheetDialog(context = context!!)
    }

    fun show() {
        show(fm, this::class.java.name)
    }

    @CheckResult
    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T?> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T?> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    @Suppress("DEPRECATION")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
        initVariable()
        initView()
        initViewObservable()
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}