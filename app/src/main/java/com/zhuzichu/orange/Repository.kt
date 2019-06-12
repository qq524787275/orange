package com.secretk.move

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.SortBean
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
    ): Observable<BaseRes<List<SortBean>>>
}