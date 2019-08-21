package com.zhuzichu.mvvm.service

import com.zhuzichu.mvvm.bean.*
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @GET("https://ifconfig.co/ip")
    fun getIpAddr(): Flowable<String>

    @FormUrlEncoded
    @POST("api/taobao/seach")
    fun searchGoods(
        @Field("pageSize") pageSize: Long,
        @Field("pageNo") pageNo: Long,
        @Field("keyWord") keyWord: String,
        @Field("sort") sort: String?
    ): Flowable<BaseRes<GoodsBean>>

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


    @POST("api/version/checkUpdate")
    fun checkUpdate(): Flowable<BaseRes<VersionBean>>
}