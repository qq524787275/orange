package com.zhuzichu.mvvm.service

import com.zhuzichu.mvvm.http.AppRetrofit

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:14
 */

private const val HAODANKU_URL = "http://v2.api.haodanku.com"

//private const val APP_URL = "http://47.111.70.169:8011"
private const val APP_URL = "http://192.168.137.1:8011"
//private const val APP_URL = "http://192.168.1.142:8011"

interface IService {
    fun getHaoDankuService(): HaoDankuService {
        return AppRetrofit.getRetrofit(HAODANKU_URL)
            .create(HaoDankuService::class.java)
    }

    fun getAppService(isEncrypt: Boolean = true, isJson: Boolean = true): AppService {
        return AppRetrofit.getRetrofit(baseUrl = APP_URL, isEncrypt = isEncrypt, isJson = isJson)
            .create(AppService::class.java)
    }
}