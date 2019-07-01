package com.zhuzichu.orange.http

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface TaobaoService {

    @GET("/awp/core/detail.htm")
    fun getShopDetailDesc(
        @Query("id") itemid: String
    ): Flowable<String>
}