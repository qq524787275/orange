package com.zhuzichu.mvvm.repository

import com.zhuzichu.mvvm.bean.AddressBean
import io.reactivex.Flowable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-14
 * Time: 10:34
 */
interface LocalRepository {
    fun getAddress(): Flowable<List<AddressBean>>

}