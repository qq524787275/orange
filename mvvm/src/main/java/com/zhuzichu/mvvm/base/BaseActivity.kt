package com.zhuzichu.mvvm.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import com.zhuzichu.mvvm.R
import kotlinx.android.synthetic.main.activity_base.*

/**
 * Created by Android Studio.
 * User: zhuzichu
 * Blog: zhuzichu.com
 * Date: 2019-05-27
 * Time: 15:12
 */

abstract class BaseActivity : RxAppCompatActivity() {
    lateinit var mNavController: NavController
    abstract fun setNavGraph(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initContainer(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_base)
        mNavController = container.findNavController()
        if (savedInstanceState == null) {
            mNavController.setGraph(setNavGraph(), savedInstanceState)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}