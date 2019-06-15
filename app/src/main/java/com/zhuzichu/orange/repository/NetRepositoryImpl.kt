package com.zhuzichu.orange.repository

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.SearchBean
import com.zhuzichu.orange.bean.ShopBean
import com.zhuzichu.orange.bean.SortBean
import com.zhuzichu.orange.http.IService
import io.reactivex.Flowable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:11
 */
object NetRepositoryImpl : NetRepository, IService {
    override fun searchShop(
        keyword: String,
        back: Int,
        sort: Int,
        cid: Int,
        min_id: Int
    ): Flowable<BaseRes<List<SearchBean>>> {
        return getHaoDankuService().searchShop(keyword, back, sort, cid, min_id)
    }

    override fun getShopSort(): Flowable<BaseRes<List<SortBean>>> {
        return getHaoDankuService().getShopSort()
    }

    override fun getShopList(
        nav: Int,
        cid: Int,
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<ShopBean>>> {
        return getHaoDankuService().getShopList(nav, cid, back, min_id)
    }


}