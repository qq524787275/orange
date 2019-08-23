package com.zhuzichu.mvvm.service

import com.zhuzichu.mvvm.bean.*
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 17:47
 */
interface HaoDankuService {

    @GET("itemlist/apikey/zhuzichu/nav/{nav}/cid/{cid}/back/{back}/min_id/{min_id}")
    fun getShopList(
        @Path("nav") nav: Int,
        @Path("cid") cid: Int,
        @Path("back") back: Int,
        @Path("min_id") min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>

    @GET("super_classify/apikey/zhuzichu")
    fun getCategory(
    ): Flowable<BaseRes<List<CategoryBean>>>

    @GET("get_keyword_items/apikey/zhuzichu/keyword/{keyword}/back/{back}/sort/{sort}/cid/{cid}/min_id/{min_id}")
    fun searchShop(
        @Path("keyword") keyword: String,
        @Path("back") back: Int,
        @Path("sort") sort: Int,
        @Path("cid") cid: Int,
        @Path("min_id") min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>

    @GET("sales_list/apikey/zhuzichu/sale_type/{sale_type}/back/{back}/min_id/{min_id}")
    fun getSalersList(
        @Path("back") back: Int,
        @Path("sale_type") sale_type: Int,
        @Path("min_id") min_id: Int
    ): Flowable<BaseRes<List<GoodsBean>>>

    @GET("hot_key/apikey/zhuzichu")
    fun getHotKeyList(): Flowable<BaseRes<List<HotKeyBean>>>

    @GET("get_deserve_item/apikey/zhuzichu")
    fun getDeserveList(): Flowable<BaseRes<List<GoodsBean>>>

    @GET("brand/apikey/zhuzichu/back/{back}/min_id/{min_id}")
    fun getBrandList(
        @Path("back") back: Int,
        @Path("min_id") min_id: Int
    ): Flowable<BaseRes<List<BrandBean>>>

    @GET("talent_info/apikey/zhuzichu/talentcat/1")
    fun getTalentcat(): Flowable<BaseRes<TalentcatBean>>

    @GET("selected_item/apikey/zhuzichu/min_id/{min_id}")
    fun getSelectedItemList(
        @Path("min_id") min_id: Int
    ): Flowable<BaseRes<List<SelectedItemBean>>>

    @GET("get_subject/apikey/zhuzichu")
    fun getSubjectList(): Flowable<BaseRes<List<SubjectBean>>>

    @GET("get_subject_item/apikey/zhuzichu/id/{id}")
    fun getSubjectItemList(
        @Path("id") id: String
    ): Flowable<BaseRes<List<SubjectItemBean>>>

    @GET("subject_hot/apikey/zhuzichu/min_id/{min_id}")
    fun getSubjectHotList(
        @Path("min_id") min_id: Int
    ): Flowable<BaseRes<List<SubjectHotBean>>>

    @GET("item_detail/apikey/zhuzichu/itemid/{itemid}")
    fun getShopDetail(
        @Path("itemid") itemid: String
    ): Flowable<BaseRes<ShopDetailBean>>

}