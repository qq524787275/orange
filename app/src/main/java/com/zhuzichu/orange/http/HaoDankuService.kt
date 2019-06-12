package com.zhuzichu.orange.http

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.SortBean
import io.reactivex.Observable
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
    @GET("http://v2.api.haodanku.com/itemlist/apikey/zhuzichu/nav/{nav}/cid/{cid}/back/{back}/min_id/{min_id}")
    fun getShopList(
        @Path("nav") nav: Int,
        @Path("cid") cid: Int,
        @Path("back") back: Int,
        @Path("min_id") min_id: Int
    ): Observable<BaseRes<List<SortBean>>>
}