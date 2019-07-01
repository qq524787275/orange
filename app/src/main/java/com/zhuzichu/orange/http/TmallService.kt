package com.zhuzichu.orange.http

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface TmallService {

    @GET("/item.htm")
    fun getShopDetailDesc(
        @Query("id") itemid: String
    ): Flowable<String>
}