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
        QMUIDisplayHelper.setFullScreen(this)
    }

    override fun setRootFragment(): ISupportFragment {
        val fragment = PictureFragment()
        fragment.arguments = intent.extras
        return fragment
    }

}
