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

private const val TAOBAO_URL = "https://item.taobao.com"

private const val TMALL_URL = "https://detail.m.tmall.com"
//private const val BASE_URL = "http://47.97.153.234:80"

interface IService {
    fun getHaoDankuService(): HaoDankuService {
        return AppRetrofit.getRetrofit(HAODANKU_URL)
            .create(HaoDankuService::class.java)
    }

    fun getTaobaoService(): TaobaoService {
        return AppRetrofit.getRetrofit(TAOBAO_URL, false)
            .create(TaobaoService::class.java)
    }

    fun getTmallService(): TmallService {
        return AppRetrofit.getRetrofit(TMALL_URL, false)
            .create(TmallService::class.java)
    }
}