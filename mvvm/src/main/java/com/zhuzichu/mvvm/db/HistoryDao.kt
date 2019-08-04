package com.zhuzichu.mvvm.db

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchHistory: SearchHistory)

    @Query("select * from search_history order by lastTime desc")
    fun queryAllSearchHistory(): Flowable<List<SearchHistory>>

    @Delete
    fun deleteSearchHistory(list: List<SearchHistory>)
}