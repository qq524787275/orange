package com.zhuzichu.mvvm.repository

import com.zhuzichu.mvvm.bean.*
import io.reactivex.Flowable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:09
 */
interface NetRepository {

    fun getIpAddr(): Flowable<String>

    fun checkUpdate(): Flowable<BaseRes<VersionBean>>

    fun getCategory(): Flowable<BaseRes<List<CategoryBean>>>

    fun getAvatarToken(): Flowable<BaseRes<String>>

    fun updateUserInfo(type: Int, value: Any): Flowable<BaseRes<UserInfoBean>>

    fun getUserInfo(): Flowable<BaseRes<UserInfoBean>>

    fun login(
        username: String,
        password: String
    ): Flowable<BaseRes<TokenBean>>

    fun getRegistCode(
        phone: String
    ): Flowable<BaseRes<String>>

    fun regist(
        username: String,
        password: String,
        phone: String,
        code: String
    ): Flowable<BaseRes<TokenBean>>

    fun getVideoList(
        cid: Int,
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>


    fun getShopList(
        nav: Int,
        cid: Int,
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>

    fun searchShop(
        keyword: String,
        back: Int,
        sort: Int,
        cid: Int,
        min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>

    fun getSalersList(
        back: Int,
        sale_type: Int,
        min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>

    fun getHotKeyList(): Flowable<BaseRes<List<HotKeyBean>>>

    fun getDeserveList(): Flowable<BaseRes<List<GoodsBean>>>

    fun getBrandList(
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<BrandBean>>>

    fun getTalentcat(): Flowable<BaseRes<TalentcatBean>>

    fun getSelectedItemList(min_id: Int): Flowable<BaseRes<List<SelectedItemBean>>>

    fun getSubjectList(): Flowable<BaseRes<List<SubjectBean>>>

    fun getSubjectItemList(
        id: String
    ): Flowable<BaseRes<List<SubjectItemBean>>>

    fun getSubjectHotList(
        min_id: Int
    ): Flowable<BaseRes<List<SubjectHotBean>>>

    fun getShopDetail(itemid: String): Flowable<BaseRes<ShopDetailBean>>

    fun getHomeHotShopList(): Flowable<BaseRes<List<GoodsBean>>>

    fun getHomeJuTaoShopList(): Flowable<BaseRes<List<GoodsBean>>>

    fun getHomeBannerList(): Flowable<BaseRes<List<GoodsBean>>>
}