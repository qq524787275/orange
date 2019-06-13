package com.secretk.move

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.SearchBean
import com.zhuzichu.orange.bean.ShopBean
import com.zhuzichu.orange.bean.SortBean
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:09
 */
interface Repository {

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
}