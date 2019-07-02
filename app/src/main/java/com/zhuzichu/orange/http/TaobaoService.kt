package com.zhuzichu.orange.http

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TaobaoService {

    @GET("/item.htm")
    fun getShopDetailDesc(
        @Query("id") itemid: String
    ): Flowable<String>

    @GET
    fun getString(
        @Url url: String
    ): Call<String>
}