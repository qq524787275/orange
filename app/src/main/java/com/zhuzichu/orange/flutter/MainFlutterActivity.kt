package com.zhuzichu.orange.flutter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil.setContentView
import io.flutter.app.FlutterActivity
import io.flutter.app.FlutterFragmentActivity
import io.flutter.facade.Flutter

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-05
 * Time: 18:20
 */
class MainFlutterActivity : FlutterFragmentActivity() {

    companion object{
       fun start(context: Context){
           val intent= Intent(context,MainFlutterActivity::class.java)
           context.startActivity(intent)
       }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mFlutterView: View = Flutter.createView(this as Activity, lifecycle, "main_flutter")
        val mParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
        addContentView(mFlutterView, mParams)
    }
}