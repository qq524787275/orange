package com.zhuzichu.mvvm.view.loading

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.zhuzichu.mvvm.R

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-11
 * Time: 18:36
 */
class LoadingDialog : AppCompatDialog {

    private val mLayoutId: Int

    constructor(context: Context?) : this(context, R.style.loading_dialog_style, R.layout.dialog_loading)


    constructor(context: Context?, style: Int, layout: Int) : super(context, style) {
        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        window!!.attributes = params
        mLayoutId = layout
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mLayoutId)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }
}