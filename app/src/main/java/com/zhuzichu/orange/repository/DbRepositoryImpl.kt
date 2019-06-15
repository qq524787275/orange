package com.zhuzichu.orange.repository

import com.zhuzichu.orange.db.AppDatabase
import com.zhuzichu.orange.db.SearchHistory
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

object DbRepositoryImpl : DbRepository {
    override fun getSearchHistoryList(): Flowable<List<SearchHistory>> {
        return AppDatabase.getInstance().historyDao().queryAllSearchHistory()
    }

    override suspend fun addSearchHistory(keyWord: String) {
        withContext(IO) {
            AppDatabase.getInstance().historyDao().insert(SearchHistory(keyWord))
        }
    }
}