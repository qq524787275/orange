package com.zhuzichu.mvvm.view.button

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-29
 * Time: 14:58
 */
class TimeButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr) {
    private var mCount = 60
    private val mHander = Handler()
    private val mTimeInterval = 1
    private lateinit var mCountDownRunnable: Runnable

    init {
        text = "获取验证码"
    }

    fun start(lifecycleOwner: LifecycleOwner) {
        mCount = 60
        updateText()
        mCountDownRunnable = Runnable {
            if (mCount <= 0) {
                text = "重新获取"
                mHander.removeCallbacks(mCountDownRunnable)
                isEnabled = true
                return@Runnable
            }
            isEnabled = false
            updateText()
            mHander.postDelayed(mCountDownRunnable, mTimeInterval * 1000L)
        }
        mHander.postDelayed(mCountDownRunnable, mTimeInterval * 1000L)
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                mHander.removeCallbacksAndMessages(null)
            }
        })
    }

    private fun updateText() {
        val info = "${mCount--}秒后重新获取"
        text = info
    }
}