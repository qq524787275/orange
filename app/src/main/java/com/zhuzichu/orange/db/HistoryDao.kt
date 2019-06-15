package com.zhuzichu.orange.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchHistory: SearchHistory)

    @Query("select * from search_history order by lastTime desc")
    fun queryAllSearchHistory(): Flowable<List<SearchHistory>>

}