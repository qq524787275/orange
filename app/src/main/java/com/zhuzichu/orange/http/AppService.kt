package com.zhuzichu.orange.http

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.TokenBean
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

}