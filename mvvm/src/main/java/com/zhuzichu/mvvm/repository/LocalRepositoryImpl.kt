package com.zhuzichu.mvvm.repository

import com.google.gson.reflect.TypeToken
import com.zhuzichu.mvvm.bean.AddressBean
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.utils.Convert
import com.zhuzichu.mvvm.utils.cast
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type

object LocalRepositoryImpl : LocalRepository {

    override fun getAddress(): Flowable<List<AddressBean>> {
        return Flowable.create({
            val jsonSB = StringBuilder()
            try {
                val addressJsonStream =
                    BufferedReader(cast(InputStreamReader(AppGlobal.context.assets.open("address.json"))))
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

}