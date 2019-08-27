package com.zhuzichu.orange

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.app.ActivityOptionsCompat
import com.trello.rxlifecycle3.components.RxActivity
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.bindToException
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.bindToSchedulers
import com.zhuzichu.mvvm.utils.helper.QMUIDisplayHelper
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.main.activity.MainActivity

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 15:31
 */
class LauncherActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        QMUIStatusBarHelper.translucent(this)
        QMUIDisplayHelper.setFullScreen(this)
        getIpAddr()
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        val animation = AlphaAnimation(0.1f, 1.0f)
        findViewById<View>(R.id.ll).animation = animation
        animation.duration = 200

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
                    finish()
                    return
                }
                val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    this@LauncherActivity.applicationContext,
                    R.anim.screen_zoom_in,
                    R.anim.screen_zoom_out
                )
                MainActivity.start(this@LauncherActivity, optionsCompat)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {
                findViewById<View>(R.id.ll).visibility = View.GONE
            }
        })
    }

    @SuppressLint("CheckResult")
    fun getIpAddr() {
        NetRepositoryImpl.getIpAddr()
            .bindToSchedulers()
            .bindToLifecycle(this)
            .bindToException()
            .subscribe({
                AppGlobal.ip.set(it)
            }, {
                it.message?.toast()
            })
    }
}