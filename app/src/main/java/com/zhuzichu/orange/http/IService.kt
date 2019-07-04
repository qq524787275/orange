package com.zhuzichu.orange.http

import com.zhuzichu.mvvm.http.AppRetrofit

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:14
 */

private const val HAODANKU_URL = "http://v2.api.haodanku.com"

//private const val BASE_URL = "http://47.97.153.234:80"

interface IService {
    fun getHaoDankuService(): HaoDankuService {
        return AppRetrofit.getRetrofit(HAODANKU_URL)
            .create(HaoDankuService::class.java)
    }
}