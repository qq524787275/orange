package com.zhuzichu.orange.repository

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.SalesBean
import com.zhuzichu.orange.bean.SearchBean
import com.zhuzichu.orange.bean.ShopBean
import com.zhuzichu.orange.bean.SortBean
import io.reactivex.Flowable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:09
 */
interface NetRepository {

    fun getShopList(
        nav: Int,
        cid: Int,
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<ShopBean>>>


    fun getShopSort(

    ): Flowable<BaseRes<List<SortBean>>>


    fun searchShop(
        keyword: String,
        back: Int,
        sort: Int,
        cid: Int,
        min_id: Int
    ): Flowable<BaseRes<List<SearchBean>>>

    fun getSalersList(
        back: Int,
        sale_type: Int,
        min_id: Int
    ):Flowable<BaseRes<List<SalesBean>>>
}