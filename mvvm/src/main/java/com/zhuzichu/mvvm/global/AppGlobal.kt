package com.zhuzichu.mvvm.global

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ali.auth.third.core.model.Session
import com.google.gson.reflect.TypeToken
import com.qiniu.android.common.FixedZone
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.UploadManager
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bean.AddressBean
import com.zhuzichu.mvvm.bean.UserInfoBean
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.global.language.LangGlobal
import com.zhuzichu.mvvm.global.language.Zh
import com.zhuzichu.mvvm.utils.Convert
import com.zhuzichu.mvvm.utils.cast
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type


object AppGlobal {

    lateinit var context: Application

    val isLogin = ObservableBoolean(false)
    val userInfo: ObservableField<UserInfoBean> = ObservableField(UserInfoBean())

    val isAuth = ObservableBoolean(false)
    val session: ObservableField<Session> = ObservableField(Session())

    fun init(context: Application) {
        AppGlobal.context = context
        //初始化颜色
        ColorGlobal.initColor()
        LangGlobal.initLang(Zh())
    }

    fun getAccount(): String? {
        return session.get()?.userid
    }

    //todo 这个方法不属于这里，以后移动
    fun getAddress(): Flowable<List<AddressBean>> {
        return Flowable.create({
            val jsonSB = StringBuilder()
            try {
                val addressJsonStream =
                    BufferedReader(cast(InputStreamReader(context.assets.open("address.json"))))
                var line: String
                while (true) {
                    //当有内容时读取一行数据，否则退出循环
                    line = addressJsonStream.readLine() ?: break
                    jsonSB.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // 将数据转换为对象
            val type: Type? = object : TypeToken<List<AddressBean>>() {}.type
            it.onNext(Convert.fromJson(jsonSB.toString(), type))
        }, BackpressureStrategy.ERROR)
    }


    private val uploadManager = UploadManager(
        Configuration
            .Builder()
            .connectTimeout(10)           // 链接超时。默认10秒
            .responseTimeout(60)          // 服务器响应超时。默认60秒
            .build()
    )

    fun getUploadManager(): UploadManager {
        return uploadManager
    }
}