package com.zhuzichu.mvvm.base

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.zhuzichu.mvvm.R

abstract class BaseDialog : AppCompatDialog {
    abstract fun initView()
    private var layoutId: Int

    constructor(context: Context?, layoutId: Int) : this(context, R.style.dialog_style, layoutId)

    constructor(context: Context?, style: Int, layout: Int) : super(context, style) {
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = params
        layoutId = layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        initView()
    }
}