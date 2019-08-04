package com.zhuzichu.mvvm.repository

import com.zhuzichu.mvvm.db.SearchHistory
import io.reactivex.Flowable

interface DbRepository {

    suspend fun addSearchHistory(keyWord: String)

    fun getSearchHistoryList(): Flowable<List<SearchHistory>>

    suspend fun deleteSearchHistory(list: List<SearchHistory>)

}