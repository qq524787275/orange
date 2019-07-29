package com.zhuzichu.mvvm.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.zhuzichu.mvvm.http.exception.ExceptionHandle
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
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
        lifecycle.bindToLifecycle()
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
        lifecycle.bindToLifecycle()
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
fun <T> schedulersTransformer(): FlowableTransformer<T, T> {
    return FlowableTransformer { observable ->
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> exceptionTransformer(): FlowableTransformer<T, T> {
    return FlowableTransformer { observable ->
        observable.onErrorResumeNext(HttpResponseFunc())
    }
}

private class HttpResponseFunc<T> : Function<Throwable, Flowable<T>> {
    override fun apply(t: Throwable): Flowable<T> {
        return Flowable.error(ExceptionHandle.handleException(t))
    }
}


fun <T> Flowable<T>.bindToException(): Flowable<T> =
    this.compose<T> {
        it.onErrorResumeNext(HttpResponseFunc())
    }

fun <T> Flowable<T>.bindToSchedulers(): Flowable<T> =
    this.compose<T> {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

fun <T, E> Flowable<T>.bindToLifecycle(provider: LifecycleProvider<E>): Flowable<T> =
    this.compose<T>(provider.bindToLifecycle<T>())
