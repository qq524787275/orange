package com.zhuzichu.orange.repository

import com.zhuzichu.orange.db.SearchHistory
import io.reactivex.Flowable

interface DbRepository {

    suspend fun addSearchHistory(keyWord: String)

    fun getSearchHistoryList(): Flowable<List<SearchHistory>>
}