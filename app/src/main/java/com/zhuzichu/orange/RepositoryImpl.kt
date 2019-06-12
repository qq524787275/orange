package com.secretk.move

import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.SortBean
import com.zhuzichu.orange.http.IService
import io.reactivex.Observable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:11
 */
object RepositoryImpl : Repository, IService {
    override fun getShopList(nav: Int, cid: Int, back: Int, min_id: Int): Observable<BaseRes<List<SortBean>>> {
        return getHaoDankuService().getShopList(nav, cid, back, min_id)
    }


}