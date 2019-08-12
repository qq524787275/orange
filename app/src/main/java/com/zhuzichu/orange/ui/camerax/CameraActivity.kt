package com.zhuzichu.orange.ui.camerax

import android.annotation.SuppressLint
import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.mvvm.utils.helper.QMUIDisplayHelper
import com.zhuzichu.orange.ui.camerax.fragment.CameraFragment
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class CameraActivity : BaseActivity() {

    companion object {
        const val EXTRA_PATH = "EXTRA_PATH"
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView: android.view.View = window.decorView
        var systemUi: Int = decorView.systemUiVisibility
        systemUi = systemUi or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            systemUi = systemUi or (android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                    or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            systemUi = systemUi or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        decorView.systemUiVisibility = systemUi
        QMUIDisplayHelper.setFullScreen(this)
    }

    override fun setRootFragment(): ISupportFragment {
        return CameraFragment()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
    }
}