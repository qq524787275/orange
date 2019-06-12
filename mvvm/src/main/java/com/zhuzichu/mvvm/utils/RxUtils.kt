package com.zhuzichu.mvvm.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.http.exception.ExceptionHandle
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 16:51
 */

/**
 * 生命周期绑定
 *
 * @param lifecycle Activity
 */
fun <T> bindToLifecycle(@NonNull lifecycle: Context): LifecycleTransformer<T> {
    return if (lifecycle is LifecycleProvider<*>) {
        (lifecycle as LifecycleProvider<*>).bindToLifecycle()
    } else {
        throw IllegalArgumentException("context not the LifecycleProvider type")
    }
}

/**
 * 生命周期绑定
 *
 * @param lifecycle Fragment
 */
fun <T> bindToLifecycle(@NonNull lifecycle: Fragment): LifecycleTransformer<T> {
    return if (lifecycle is LifecycleProvider<*>) {
        (lifecycle as LifecycleProvider<*>).bindToLifecycle()
    } else {
        throw IllegalArgumentException("fragment not the LifecycleProvider type")
    }
}

/**
 * 生命周期绑定
 *
 * @param lifecycle Fragment
 */
fun <T> bindToLifecycle(@NonNull lifecycle: LifecycleProvider<*>): LifecycleTransformer<T> {
    return lifecycle.bindToLifecycle()
}

/**
 * 线程调度器
 */
/**
 * 线程调度器
 */
fun <T> schedulersTransformer(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> exceptionTransformer(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable.onErrorResumeNext(HttpResponseFunc())
    }
}

private class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
    override fun apply(t: Throwable): Observable<T> {
        return Observable.error(ExceptionHandle.handleException(t))
    }
}