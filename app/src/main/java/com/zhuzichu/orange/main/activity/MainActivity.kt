package com.zhuzichu.orange.main.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.AppPreference
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.widget.FadeAnimator
import com.zhuzichu.orange.main.fragment.MainFragment
import com.zhuzichu.orange.view.plane.PlaneMaker
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MainActivity : BaseActivity() {

    val preference by lazy { AppPreference() }


    override fun setRootFragment(): ISupportFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (preference.isDark)
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        else
            QMUIStatusBarHelper.setStatusBarLightMode(this)

        ColorGlobal.isDark.observe(this, Observer {
            window.setBackgroundDrawable(ColorGlobal.windowBackground.get()?.let { color -> ColorDrawable(color) })
        })
    }

    companion object {
        fun start(context: Context, options: ActivityOptionsCompat? = null) {
            val intent = Intent()
            intent.setClass(context, MainActivity::class.java)
            if (options != null)
                context.startActivity(intent, options.toBundle())
            else
                context.startActivity(intent)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return FadeAnimator()
    }


    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
    }

    override fun onResume() {
        super.onResume()
        PlaneMaker.dismissLodingDialog()
    }
}
