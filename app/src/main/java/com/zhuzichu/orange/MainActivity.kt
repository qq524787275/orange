package com.zhuzichu.orange

import android.content.Context
import android.content.Intent
import com.zhuzichu.mvvm.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent()
            intent.setClass(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun setNavGraph(): Int = R.navigation.navigation_main

    override fun onSupportNavigateUp(): Boolean = true


}
