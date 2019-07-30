package com.zhuzichu.orange.http

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.mvvm.bean.TokenBean
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppService {

    @FormUrlEncoded
    @POST("api/user/regist")
    fun regist(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Flowable<BaseRes<TokenBean>>


    @FormUrlEncoded
    @POST("api/sms/getRegistCode")
    fun getRegistCode(
        @Field("phone") phone: String
    ): Flowable<BaseRes<String>>


    @FormUrlEncoded
    @POST("api/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Flowable<BaseRes<TokenBean>>

}