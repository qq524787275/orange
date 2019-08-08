package com.zhuzichu.mvvm.service

import com.zhuzichu.mvvm.bean.BaseRes
import com.zhuzichu.mvvm.bean.CategoryBean
import com.zhuzichu.mvvm.bean.TokenBean
import com.zhuzichu.mvvm.bean.UserInfoBean
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppService {

    @FormUrlEncoded
    @POST("api/category/getCategory")
    fun getCategory(
        @Field("pid") pid: Long
    ): Flowable<BaseRes<List<CategoryBean>>>

    @POST("api/user/getAvatarToken")
    fun getAvatarToken(): Flowable<BaseRes<String>>

    @FormUrlEncoded
    @POST("api/user/updateUserInfo")
    fun updateUserInfo(
        @Field("type") type: Int,
        @Field("value") value: Any
    ): Flowable<BaseRes<UserInfoBean>>

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


    @POST("api/user/getUserInfo")
    fun getUserInfo(): Flowable<BaseRes<UserInfoBean>>
}