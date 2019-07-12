package com.zhuzichu.mvvm.base

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.zhuzichu.mvvm.utils.toStringById
import com.zhuzichu.mvvm.utils.toast
import kotlin.system.exitProcess


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-11
 * Time: 10:54
 */
abstract class BaseMainFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseFragment<V, VM>() {
    // 再点一次退出程序时间设置
    private val waitTime = 2000L
    private var touchTime: Long = 0

    @SuppressLint("CheckResult")
    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis() - touchTime < waitTime) {
            _viewModel._activity.finish()
            Thread {
                Thread.sleep(300)
                exitProcess(0)
            }.start()
        } else {
            touchTime = System.currentTimeMillis()
            com.zhuzichu.mvvm.R.string.press_again_exit.toStringById().toast()
        }
        return true
    }
}