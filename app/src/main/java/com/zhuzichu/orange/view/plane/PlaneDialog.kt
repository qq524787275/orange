package com.zhuzichu.orange.view.plane
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.zhuzichu.orange.R

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-11
 * Time: 18:36
 */
class PlaneDialog : AppCompatDialog {

    private val mLayoutId: Int

    constructor(context: Context?) : this(context, R.style.dialog_style, R.layout.dialog_plane)


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