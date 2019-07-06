package com.zhuzichu.orange.flutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-05
 * Time: 18:20
 */
class MainFlutterActivity : FlutterActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainFlutterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        intent.putExtra("enable-software-rendering", true)
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
    }
}