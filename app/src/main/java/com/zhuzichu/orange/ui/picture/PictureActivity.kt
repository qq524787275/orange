package com.zhuzichu.orange.ui.picture

import android.annotation.SuppressLint
import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.mvvm.utils.helper.QMUIDisplayHelper
import com.zhuzichu.orange.ui.picture.fragment.PictureFragment
import me.yokeyword.fragmentation.ISupportFragment

class PictureActivity : BaseActivity() {

    companion object {
        const val URLS = "LIST"
        const val POSITION = "POSITION"
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
        val fragment = PictureFragment()
        fragment.arguments = intent.extras
        return fragment
    }

}
